package com.nghex.exe202.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;
    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Long userID;
}
