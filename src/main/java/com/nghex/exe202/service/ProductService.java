package com.nghex.exe202.service;

import com.nghex.exe202.dto.ProductAdminDto;
import com.nghex.exe202.dto.ProductDetailDto;
import com.nghex.exe202.dto.ProductTop10Dto;
import com.nghex.exe202.dto.SearchProductDto;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req,

                                 Seller seller) throws ProductException;
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,CreateProductRequest product)throws ProductException;
    public Product updateProductStock(Long productId)throws ProductException;
    public Product findProductById(Long id) throws ProductException;
    public List<Product> searchProduct(String query);

    ProductDetailDto productDetail(Long id) throws ProductException;


    Page<Product> getAllProduct(SearchProductDto filter);
    public List<Product> recentlyAddedProduct();
    List<Product> getProductBySellerId(Long sellerId);


    List<ProductTop10Dto> getTop10();

    List<ProductAdminDto> getAllProductByAdmin();

    void deleteById(Long id);
}