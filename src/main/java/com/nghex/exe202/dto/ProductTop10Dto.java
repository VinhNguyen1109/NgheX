package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductTop10Dto {
    private String name;
    private String categoryName;
    private int price;
    private String image;
//    public ProductTop10Dto(String name, String categoryName, int price, String image) {
//        this.name = name;
//        this.categoryName = categoryName;
//        this.price = price;
//        this.image = image;
//    }
}
