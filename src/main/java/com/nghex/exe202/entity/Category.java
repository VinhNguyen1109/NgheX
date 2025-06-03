package com.nghex.exe202.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    @NotNull
    @Column(unique = true)
    private String categoryId;
    @ManyToOne
    private Category parentCategory;
    @NotNull
    private Integer level;

    @Column(name = "image", columnDefinition = "NVARCHAR(500)")
    private String image;

}
