package com.nghex.exe202.service;


import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.exception.CartItemException;
import com.nghex.exe202.exception.UserException;

public interface CartItemService {
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
