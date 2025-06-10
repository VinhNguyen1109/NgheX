package com.nghex.exe202.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "cart", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CartItem> cartItems = new HashSet<>();
    private double totalSellingPrice;
    private int totalItem;
    private int totalMrpPrice;
    private int Discount;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String couponCode;
    private int couponPrice;
}
