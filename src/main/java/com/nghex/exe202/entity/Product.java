package com.nghex.exe202.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String title;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private int discountPercent;
    private int quantity;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String color;
    @ElementCollection
    private List<String> images = new ArrayList<>();
    private int numRating;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Seller seller;
    private LocalDateTime createdAt;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String Sizes;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    private boolean in_stock = true;
}
