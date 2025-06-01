package com.nghex.exe202.controller;

import com.nghex.exe202.dto.ProductTop10Dto;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.service.ProductService;
import com.nghex.exe202.service.SellerService;
import com.nghex.exe202.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    private final UserService userService;

    private final SellerService sellerService;


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {

        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(required = false) String query) {
        List<Product> products = productService.searchProduct(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(required = false) String category,
                                                        @RequestParam(required = false) String brand,
                                                        @RequestParam(required = false) String color,
                                                        @RequestParam(required = false) String size,
                                                        @RequestParam(required = false) Integer minPrice,
                                                        @RequestParam(required = false) Integer maxPrice,
                                                        @RequestParam(required = false) Integer minDiscount,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String stock,
                                                        @RequestParam(defaultValue = "0") Integer pageNumber) {
        System.out.println("color p -------- " + pageNumber);
        return new ResponseEntity<>(
                productService.getAllProduct(category, brand,
                        color, size, minPrice,
                        maxPrice, minDiscount, sort,
                        stock, pageNumber), HttpStatus.OK);
    }

    @GetMapping("/getTop10")
    public ResponseEntity<List<ProductTop10Dto>> getTop10() {
        List<ProductTop10Dto> products = productService.getTop10();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
