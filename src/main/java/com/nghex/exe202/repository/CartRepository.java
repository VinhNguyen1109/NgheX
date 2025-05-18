package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

}
