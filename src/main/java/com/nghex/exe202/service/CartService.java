package com.nghex.exe202.service;


import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.ProductException;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	public Cart findUserCart(User user);

	public void clearCart(User user);
}
