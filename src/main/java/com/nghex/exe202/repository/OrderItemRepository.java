package com.nghex.exe202.repository;

import com.nghex.exe202.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

