package com.nghex.exe202.service;

import com.nghex.exe202.dto.CartDTO;
import com.nghex.exe202.dto.CartItemDTO;
import com.nghex.exe202.dto.ProductDTO2;
import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartDTO toDTO(Cart cart) {
        List<CartItemDTO> cartItems = cart.getCartItems().stream()
                .map(CartMapper::toDTO)
                .collect(Collectors.toList());

        return new CartDTO(
                cart.getId(),
                cartItems,
                cart.getTotalSellingPrice(),
                cart.getTotalItem(),
                cart.getTotalMrpPrice(),
                cart.getDiscount(),
                cart.getCouponCode(),
                cart.getCouponPrice()
        );
    }

    public static CartItemDTO toDTO(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getSize(),
                item.getQuantity(),
                item.getMrpPrice(),
                item.getSellingPrice(),
                toProductDTO(item.getProduct()),
                item.getUserID()
        );
    }   

    public static ProductDTO2 toProductDTO(Product product) {
        if (product == null) return null;

        return new ProductDTO2(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getMrpPrice(),
                product.getSellingPrice(),
                product.getDiscountPercent(),
                product.getQuantity(),
                product.getColor(),
                product.getImages(),
                product.isIn_stock(), // hoặc getIn_stock()
                product.getSizes()
        );
    }
}
