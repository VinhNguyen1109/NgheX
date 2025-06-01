package com.nghex.exe202.entity;

import com.nghex.exe202.util.enums.HomeCategorySection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String image;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String categoryId;
    private HomeCategorySection section;
}
