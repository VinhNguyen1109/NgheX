package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO2 {
    private Long id;
    private String title;
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private int discountPercent;
    private int quantity;
    private String color;
    private List<String> images;
    private boolean inStock;
    private String sizes;
}
