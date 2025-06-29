package com.nghex.exe202.controller;

import com.nghex.exe202.dto.ProductAdminDto;
import com.nghex.exe202.dto.ProductDetailDto;
import com.nghex.exe202.dto.ProductTop10Dto;
import com.nghex.exe202.dto.SearchProductDto;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.service.EmailService;
import com.nghex.exe202.service.ProductService;
import com.nghex.exe202.service.SellerService;
import com.nghex.exe202.service.UserService;
import lombok.Data;
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

    private final EmailService emailService;


    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/get-all-product")
    public ResponseEntity<?> getAllProductsAdmin() {
        return new ResponseEntity<>(productService.getAllProductByAdmin() ,HttpStatus.OK);
    }

    @PostMapping("/send-warning")
    public ResponseEntity<?> sendWarning(@RequestBody ProductAdminDto data) {
        emailService.sendWarningProductEmail(data.getSellerEmail(), data.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {

        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable Long productId) throws ProductException {
        ProductDetailDto dto = productService.productDetail(productId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(required = false) String query) {
        List<Product> products = productService.searchProduct(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(required = false) Integer category,
                                                        @RequestParam(required = false) String brand,
                                                        @RequestParam(required = false) String color,
                                                        @RequestParam(required = false) String size,
                                                        @RequestParam(required = false) Integer minPrice,
                                                        @RequestParam(required = false) Integer maxPrice,
                                                        @RequestParam(required = false) Integer minDiscount,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String stock,
                                                        @RequestParam(defaultValue = "0") Integer pageNumber) {
        System.out.println("get product " + pageNumber);
        SearchProductDto data = SearchProductDto.builder()
                .categoryId(category)
                .brand(brand)
                .color(color)
                .size(size)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minDiscount(minDiscount)
                .sort(sort)
                .pageNumber(pageNumber)
                .build();
        return new ResponseEntity<>(
                productService.getAllProduct(data), HttpStatus.OK);
    }

    @GetMapping("/getTop10")
    public ResponseEntity<List<ProductTop10Dto>> getTop10() {
        List<ProductTop10Dto> products = productService.getTop10();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

//    @PutMapping("/api/products/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        try {
//            Product updatedProduct = productService.updateProduct(id, product);
//            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//        } catch (ProductException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

}
