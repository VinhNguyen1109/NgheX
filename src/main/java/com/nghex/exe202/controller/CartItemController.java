package com.nghex.exe202.controller;

import com.nghex.exe202.service.CartItemService;
import com.nghex.exe202.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	private CartItemService cartItemService;
	private UserService userService;
	
	public CartItemController(CartItemService cartItemService, UserService userService) {
		this.cartItemService=cartItemService;
		this.userService=userService;
	}
	

}
