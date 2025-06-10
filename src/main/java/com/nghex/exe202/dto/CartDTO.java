package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalSellingPrice;
    private int totalItem;
    private int totalMrpPrice;
    private int discount;
    private String couponCode;
    private int couponPrice;
}
