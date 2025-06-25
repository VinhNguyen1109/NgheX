package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private ProductDTO2 product;
    private Long userID;
}