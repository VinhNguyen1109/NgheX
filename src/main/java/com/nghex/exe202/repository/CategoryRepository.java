package com.nghex.exe202.repository;


import com.nghex.exe202.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String categoryId);
    List<Category> findByLevel(Integer level);

    @Query("SELECT c FROM Category c ORDER BY c.categoryId DESC")
    List<Category> findTop10ByCategoryIdDesc(Pageable pageable);

    List<Category> findByParentCategoryId(Long id);

    List<Category> getTop10ByLevel(Integer level);

}
