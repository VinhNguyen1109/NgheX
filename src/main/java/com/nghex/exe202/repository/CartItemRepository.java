package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);

}
