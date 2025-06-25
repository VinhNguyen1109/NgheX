package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPaymentDto {
    private int price;
    private String productName;
    private String productDescription;
    private String returnUrl;
    private String cancelUrl;
}
