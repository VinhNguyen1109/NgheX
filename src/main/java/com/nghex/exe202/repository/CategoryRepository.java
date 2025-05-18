package com.nghex.exe202.repository;


import com.nghex.exe202.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String categoryId);
    List<Category> findByLevel(Integer level);

}
