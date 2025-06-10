package com.nghex.exe202.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"cartItems", "user"}) // ❗ tránh in vòng lặp
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // ✅ chỉ so sánh bằng ID
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

