package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchProductDto {
    private Integer categoryId;
    private String brand;
    private String color;
    private String size;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minDiscount;
    private String sort;
    private Boolean stock;
    private Integer pageNumber;
}
