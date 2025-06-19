package com.nghex.exe202.service.impl;


import com.nghex.exe202.configuration.JwtProvider;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.repository.PasswordResetTokenRepository;
import com.nghex.exe202.repository.UserRepository;
import com.nghex.exe202.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
//	private final JavaMailSender javaMailSender;


	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email=jwtProvider.getEmailFromJwtToken(jwt);

		System.out.println("check email " + email);
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		return user;
	}



	
	@Override
	public User findUserByEmail(String username) throws UserException {
		
		User user=userRepository.findByEmail(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}



}
