package com.nuan.storefront.adapters.web.controller;

import com.nuan.storefront.adapters.web.dto.ProductDTO;
import com.nuan.storefront.application.usecases.GetProductsUseCase;
import com.nuan.storefront.domain.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for product endpoints.
 * Handles HTTP requests and delegates to use cases.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final GetProductsUseCase getProductsUseCase;

    public ProductController(GetProductsUseCase getProductsUseCase) {
        this.getProductsUseCase = getProductsUseCase;
    }

    /**
     * GET /api/products - Get all products
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = getProductsUseCase.execute();
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    /**
     * GET /api/products/category/{category} - Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = getProductsUseCase.executeByCategory(category);
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    /**
     * GET /api/products/{id} - Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = getProductsUseCase.executeGetById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(convertToDTO(product.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found with id: " + id);
    }

    /**
     * GET /api/products/search/{searchTerm} - Search for products
     */
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<ProductDTO>> getProductBySearchTerm(@PathVariable String searchTerm) {
        List<Product> products = getProductsUseCase.executeSearch(searchTerm);
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    /**
     * Convert Product entity to ProductDTO
     */
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getStock(),
                product.getImageUrl()
        );
    }
}

