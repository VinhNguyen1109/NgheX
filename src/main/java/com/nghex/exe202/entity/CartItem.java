package com.nghex.exe202.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"cart", "product"}) // ❗ tránh in vòng lặp
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Long userID;
}
