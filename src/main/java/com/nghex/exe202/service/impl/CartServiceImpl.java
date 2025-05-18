package com.nghex.exe202.service.impl;

import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.repository.CartItemRepository;
import com.nghex.exe202.repository.CartRepository;
import com.nghex.exe202.service.CartService;
import com.nghex.exe202.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductService productService;
	

	public Cart findUserCart(User user) {
		Cart cart =	cartRepository.findByUserId(user.getId());

		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		for(CartItem cartsItem : cart.getCartItems()) {
			totalPrice+=cartsItem.getMrpPrice();
			totalDiscountedPrice+=cartsItem.getSellingPrice();
			totalItem+=cartsItem.getQuantity();
		}

		cart.setTotalMrpPrice(totalPrice);
		cart.setTotalItem(cart.getCartItems().size());
		cart.setTotalSellingPrice(totalDiscountedPrice-cart.getCouponPrice());
		cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountedPrice));
		cart.setTotalItem(totalItem);
		
		return cartRepository.save(cart);
		
	}

	public static int calculateDiscountPercentage(double mrpPrice, double sellingPrice) {
		if (mrpPrice <= 0) {
			return 0;
		}
		double discount = mrpPrice - sellingPrice;
		double discountPercentage = (discount / mrpPrice) * 100;
		return (int) discountPercentage;
	}

	@Override
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity
								) throws ProductException {
		Cart cart=findUserCart(user);
		
		CartItem isPresent=cartItemRepository.findByCartAndProductAndSize(
				cart, product, size);
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);

			cartItem.setQuantity(quantity);
			cartItem.setUserID(user.getId());

			int totalPrice=quantity*product.getSellingPrice();
			cartItem.setSellingPrice(totalPrice);
			cartItem.setMrpPrice(quantity*product.getMrpPrice());
			cartItem.setSize(size);

			cart.getCartItems().add(cartItem);
			cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
		}

		return isPresent;
	}

}
