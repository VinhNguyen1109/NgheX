package com.nghex.exe202.service.impl;

import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.CartItemException;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.repository.CartItemRepository;
import com.nghex.exe202.service.CartItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	private CartItemRepository cartItemRepository;

	@Autowired
	public CartItemServiceImpl(CartItemRepository cartItemRepository) {
		this.cartItemRepository=cartItemRepository;

	}

	@Override
	public CartItem updateCartItem(Long userId,
								   Long id, CartItem cartItem)
			throws CartItemException, UserException {
		
		CartItem item=findCartItemById(id);
		User cartItemUser=item.getCart().getUser();
		
		if(cartItemUser.getId().equals(userId)) {
			
			item.setQuantity(cartItem.getQuantity());
			item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
			item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
			
			return cartItemRepository.save(item);
				
			
		}
		else {
			throw new CartItemException("You can't update  another users cart_item");
		}
		
	}
	

	@Override
	@Transactional
	public void removeCartItem(Long userId,Long cartItemId)
			throws CartItemException,
			UserException {
		
		System.out.println("userId- "+userId+" cartItemId "+cartItemId);
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User cartItemUser=cartItem.getCart().getUser();

		if(cartItemUser.getId().equals(userId)) {
			System.out.println("check cartItemId "+cartItemId);
			try {
				cartItemRepository.deleteCartItemById(cartItemId);
				System.out.println("chjeckkkkk");
			}catch (Exception e) {
				e.printStackTrace();
			}


		}
		else {
			throw new UserException("you can't remove another users item");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id : "+cartItemId);
	}

}
