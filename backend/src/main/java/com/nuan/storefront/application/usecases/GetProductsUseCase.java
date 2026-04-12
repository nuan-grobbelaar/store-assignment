package com.nuan.storefront.application.usecases;

import com.nuan.storefront.domain.entity.Product;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import java.util.List;
import java.util.Optional;

/**
 * Use case for retrieving products.
 * Contains business logic for product queries.
 */
public class GetProductsUseCase {
    private final ProductRepositoryPort productRepository;

    public GetProductsUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieve all products.
     */
    public List<Product> execute() {
        return productRepository.findAll();
    }

    /**
     * Retrieve products by category.
     */
    public List<Product> executeByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Search products by query string.
     */
    public List<Product> executeSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.search(query);
    }

    /**
     * Get product by ID.
     */
    public Optional<Product> executeGetById(Long productId) {
        return productRepository.findById(productId);
    }
}

