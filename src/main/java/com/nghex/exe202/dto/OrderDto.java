package com.nghex.exe202.dto;

import com.nghex.exe202.entity.Address;
import com.nghex.exe202.model.PaymentDetails;
import com.nghex.exe202.util.enums.OrderStatus;
import com.nghex.exe202.util.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String orderId;
    private UserDto user;
    private Long sellerId;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private Address shippingAddress;
    private PaymentDetails paymentDetails=new PaymentDetails();
    private double totalMrpPrice;
    private Integer totalSellingPrice;
    private Integer discount;
    private OrderStatus orderStatus;
    private int totalItem;
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;
    private LocalDateTime orderDate = LocalDateTime.now();
    private LocalDateTime deliverDate = orderDate.plusDays(7);
}
