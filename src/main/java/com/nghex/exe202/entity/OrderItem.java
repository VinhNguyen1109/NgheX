package com.nghex.exe202.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private  Order order;
    @ManyToOne
    private Product product;
    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Long userId;
}
