package com.nuan.storefront.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuan.storefront.application.usecases.GetProductsUseCase;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    /**
     * Create ObjectMapper bean for JSON serialization/deserialization
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Create GetProductsUseCase with ProductRepositoryPort dependency
     */
    @Bean
    public GetProductsUseCase getProductsUseCase(ProductRepositoryPort productRepository) {
        return new GetProductsUseCase(productRepository);
    }
}

