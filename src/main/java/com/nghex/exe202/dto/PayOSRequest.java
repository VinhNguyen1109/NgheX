package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayOSRequest {
    private String orderCode;
    private int amount;
    private String description;

    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;

    private List<Item> items;

    private String cancelUrl;
    private String returnUrl;
    private long expiredAt;
    private String signature;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String name;
        private int quantity;
        private int price;
    }
}