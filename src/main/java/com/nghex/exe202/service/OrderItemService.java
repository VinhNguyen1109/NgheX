package com.nghex.exe202.service;

import com.nghex.exe202.entity.OrderItem;

public interface OrderItemService {
	OrderItem getOrderItemById(Long id) throws Exception;
}
