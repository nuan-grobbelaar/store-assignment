package com.nuan.storefront.domain.port;

import com.nuan.storefront.domain.entity.Product;
import java.util.List;
import java.util.Optional;

/**
 * Port for product persistence.
 * Defines how the domain interacts with product storage (abstraction of database/file system).
 */
public interface ProductRepositoryPort {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> search(String query);
}

