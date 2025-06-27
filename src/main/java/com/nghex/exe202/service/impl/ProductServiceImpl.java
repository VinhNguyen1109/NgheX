package com.nghex.exe202.service.impl;

import com.nghex.exe202.dto.ProductAdminDto;
import com.nghex.exe202.dto.ProductTop10Dto;
import com.nghex.exe202.dto.SearchProductDto;
import com.nghex.exe202.entity.Category;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.repository.CategoryRepository;
import com.nghex.exe202.repository.ProductRepository;
import com.nghex.exe202.request.CreateProductRequest;
import com.nghex.exe202.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    
    private final CategoryRepository categoryRepository;

    public static int calculateDiscountPercentage(double mrpPrice, double sellingPrice) {
        if (mrpPrice <= 0) {
            throw new IllegalArgumentException("Actual price must be greater than zero.");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        Product product=findProductById(productId);
        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        productRepository.findById(productId);
        product.setId(productId);
        return productRepository.save(product);

    }

    @Override
    public Product updateProductStock(Long productId) throws ProductException {
        Product product = this.findProductById(productId);
        product.setIn_stock(!product.isIn_stock());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductException("product not found"));
    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProduct(SearchProductDto filter) {
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Category filter (match categoryId or parentCategoryId)
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            if (filter.getBrand() != null && !filter.getBrand().isEmpty()) {
                predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
            }

            if (filter.getColor() != null && !filter.getColor().isEmpty()) {
                predicates.add(cb.equal(root.get("color"), filter.getColor()));
            }

            if (filter.getSize() != null && !filter.getSize().isEmpty()) {
                predicates.add(cb.equal(root.get("size"), filter.getSize()));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("sellingPrice"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("sellingPrice"), filter.getMaxPrice()));
            }

            if (filter.getMinDiscount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("discountPercent"), filter.getMinDiscount()));
            }

            if (filter.getStock() != null) {
                predicates.add(cb.equal(root.get("stock"), filter.getStock()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        int page = filter.getPageNumber() != null && filter.getPageNumber() >= 0 ? filter.getPageNumber() : 0;
        Pageable pageable = createPageable(filter.getSort(), page);

        return productRepository.findAll(spec, pageable);
    }
    private Pageable createPageable(String sort, int pageNumber) {
        final int PAGE_SIZE = 10;

        if (sort == null || sort.isEmpty()) {
            return PageRequest.of(pageNumber, PAGE_SIZE);
        }

        return switch (sort) {
            case "price_low" -> PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("sellingPrice").ascending());
            case "price_high" -> PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("sellingPrice").descending());
            default -> PageRequest.of(pageNumber, PAGE_SIZE);
        };
    }



    @Override
    public List<Product> recentlyAddedProduct() {
        return List.of();
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public List<ProductTop10Dto> getTop10() {
        List<Product> products = productRepository.findTopProducts(PageRequest.of(0, 10));

        return products.stream().map(p -> {
            String firstImage = (p.getImages() != null && !p.getImages().isEmpty()) ? p.getImages().get(0) : null;
            return new ProductTop10Dto(
                    p.getTitle(),
                    p.getCategory() != null ? p.getCategory().getName() : null,
                    p.getSellingPrice(),
                    firstImage
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductAdminDto> getAllProductByAdmin() {
        List<Product> products = productRepository.findAll();
        List<ProductAdminDto> productAdminDtos = new ArrayList<>();
        for(Product product : products) {
            ProductAdminDto productAdminDto = ProductAdminDto.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .images(product.getImages())
                    .sellerEmail(product.getSeller().getEmail())
                    .build();
            productAdminDtos.add(productAdminDto);
        }
        return productAdminDtos;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) throws ProductException {
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());


        Category category1 = categoryRepository.findById(Long.parseLong(req.getCategory()))
                .orElseThrow(() -> new ProductException("Category1 not found"));
        Category category2 = categoryRepository.findById(Long.parseLong(req.getCategory2()))
                .orElseThrow(() -> new ProductException("Category2 not found"));
        Category category3 = categoryRepository.findById(Long.parseLong(req.getCategory3()))
                .orElseThrow(() -> new ProductException("Category3 not found"));

        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPercent(discountPercentage);
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setCreatedAt(LocalDateTime.now());
        product.setQuantity(1000);

        return productRepository.save(product);
    }

}
