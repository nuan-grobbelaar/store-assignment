package com.nuan.storefront.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nuan.storefront.application.usecases.CreateOrderUseCase;
import com.nuan.storefront.application.usecases.GetProductsUseCase;
import com.nuan.storefront.domain.port.OrderRepositoryPort;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import com.nuan.storefront.infrastructure.adapter.JsonFileOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    /**
     * Create ObjectMapper bean for JSON serialization/deserialization
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    /**
     * Create GetProductsUseCase with ProductRepositoryPort dependency
     */
    @Bean
    public GetProductsUseCase getProductsUseCase(ProductRepositoryPort productRepository) {
        return new GetProductsUseCase(productRepository);
    }

    @Bean
    public OrderRepositoryPort orderRepository() {
        ObjectMapper fileMapper = new ObjectMapper();
        fileMapper.registerModule(new JavaTimeModule());
        fileMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String path = System.getProperty("user.dir") + "/orders.json";
        return new JsonFileOrderRepository(fileMapper, path);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepository, ProductRepositoryPort productRepository) {
        return new CreateOrderUseCase(orderRepository, productRepository);
    }
}

