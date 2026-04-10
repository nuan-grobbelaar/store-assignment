package com.nuan.storefront.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuan.storefront.domain.entity.Product;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Product repository adapter that loads products from JSON file.
 * Implements the ProductRepositoryPort interface.
 */
@Repository
public class JsonFileProductRepository implements ProductRepositoryPort {

    private final ObjectMapper objectMapper;
    private List<Product> products;

    public JsonFileProductRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadProducts();
    }

    /**
     * Load products from the products.json file in resources
     */
    private void loadProducts() {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("products.json");
            if (inputStream == null) {
                throw new IllegalStateException("products.json file not found in resources");
            }
            Product[] productArray = objectMapper.readValue(inputStream, Product[].class);
            this.products = new ArrayList<>(Arrays.asList(productArray));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load products from JSON file", e);
        }
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return findAll();
        }
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        String searchTerm = query.toLowerCase().trim();
        return products.stream()
                .filter(product ->
                    product.getName().toLowerCase().contains(searchTerm) ||
                    product.getDescription().toLowerCase().contains(searchTerm) ||
                    product.getBrand().toLowerCase().contains(searchTerm) ||
                    product.getCategory().toLowerCase().contains(searchTerm)
                )
                .collect(Collectors.toList());
    }
}

