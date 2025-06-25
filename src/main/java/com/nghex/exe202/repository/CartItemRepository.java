package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
    List<CartItem> getAllByCartId(Long cartId);
}
