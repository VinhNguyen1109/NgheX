package com.nghex.exe202.response;

import com.nghex.exe202.dto.OrderHistory;
import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;
}
