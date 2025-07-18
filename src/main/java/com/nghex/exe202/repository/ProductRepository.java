package com.nghex.exe202.repository;

import com.nghex.exe202.dto.ProductTop10Dto;
import com.nghex.exe202.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
    List<Product> findBySellerId(Long shopId);
//    @Query("SELECT p FROM Product p WHERE (:query IS NULL OR LOWER(p.title) " +
//            "LIKE LOWER(CONCAT('%', :query, '%'))) " +
//            "OR (:query IS NULL OR LOWER(p.category.name) " +
//            "LIKE LOWER(CONCAT('%', :query, '%')))"+
//            "OR (:query IS NULL OR LOWER(p.category.categoryId) " +
//            "LIKE LOWER(CONCAT('%', :query, '%')))"
//    )
//    List<Product> searchProduct(@Param("query") String query);

    @Query("SELECT p FROM Product p WHERE (:query IS NULL OR LOWER(p.title)" +
            " LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(p.category.name)" +
            " LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(p.category.categoryId)" +
            " LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> searchProduct(@Param("query") String query);


    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    List<Product> findTopProducts(Pageable pageable);

    @Query("select p from Product p")
    List<Product> getAllProduct();

}