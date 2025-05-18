package com.nghex.exe202.service.impl;


import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.repository.SellerRepository;
import com.nghex.exe202.repository.UserRepository;
import com.nghex.exe202.util.enums.USER_ROLE;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final SellerRepository sellerRepository;
	private static final String SELLER_PREFIX = "seller_";

	public CustomUserServiceImpl(UserRepository userRepository, SellerRepository sellerRepository) {
		this.userRepository = userRepository;
		this.sellerRepository = sellerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.startsWith(SELLER_PREFIX)) {
			// Remove prefix to get the actual username/email
			String actualUsername = username.substring(SELLER_PREFIX.length());
			Seller seller = sellerRepository.findByEmail(actualUsername);
			if (seller != null) {
				return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
			}
		} else {
			User user = userRepository.findByEmail(username);
			if (user != null) {
				return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
			}
		}

		throw new UsernameNotFoundException("User or Seller not found with email - " + username);
	}

	private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
		if (role == null) role = USER_ROLE.ROLE_CUSTOMER;

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));

		return new org.springframework.security.core.userdetails.User(email, password, authorities);
	}
}
