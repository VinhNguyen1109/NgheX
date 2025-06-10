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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public Cart findUserCart(User user) {
        try {
            Cart cart = cartRepository.findByUserId(user.getId());

            if (cart == null) {
                cart = Cart.builder()
                        .user(user)
                        .build();
                return cartRepository.save(cart);
            }

            Set<CartItem> cartItems = cart.getCartItems(); // Hibernate sẽ tự fetch

            int totalPrice = 0;
            int totalDiscountedPrice = 0;
            int totalItem = 0;

            for (CartItem item : cartItems) {
                totalPrice += item.getMrpPrice();
                totalDiscountedPrice += item.getSellingPrice();
                totalItem += item.getQuantity();
            }

            cart.setTotalMrpPrice(totalPrice);
            cart.setTotalItem(totalItem);
            cart.setTotalSellingPrice(totalDiscountedPrice - cart.getCouponPrice());
            cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));

            return cartRepository.save(cart);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        Cart cart = findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(
                cart, product, size);

        if (isPresent == null) {
            System.out.println("Creating new cart item");
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserID(user.getId());

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity * product.getMrpPrice());
            cartItem.setSize(size);
            cartItem.setCart(cart); // Set cart trước

            // Save CartItem
            CartItem savedItem = cartItemRepository.save(cartItem);

            // Update Cart
            cart.getCartItems().add(savedItem);
            // cartRepository.save(cart); // Nếu cần thiết

            System.out.println("Saved cart item with ID: " + savedItem.getId());
            return savedItem; // ← Return item mới tạo
        } else {
            System.out.println("Cart item already exists");
            // Có thể update quantity nếu cần
            isPresent.setQuantity(isPresent.getQuantity() + quantity);
            return cartItemRepository.save(isPresent);
        }
    }

}
