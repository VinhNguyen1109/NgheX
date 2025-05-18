package com.nghex.exe202.service;

import com.nghex.exe202.entity.Address;
import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.Order;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.OrderException;
import com.nghex.exe202.util.enums.OrderStatus;

import java.util.List;
import java.util.Set;

public interface OrderService {
	
	public Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
	public Order findOrderById(Long orderId) throws OrderException;
	public List<Order> usersOrderHistory(Long userId);
	public List<Order>getShopsOrders(Long sellerId);
	public Order updateOrderStatus(Long orderId,
								   OrderStatus orderStatus)
			throws OrderException;
	public void deleteOrder(Long orderId) throws OrderException;
	Order cancelOrder(Long orderId,User user) throws OrderException;
	
}
