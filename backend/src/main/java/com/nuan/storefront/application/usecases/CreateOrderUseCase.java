package com.nuan.storefront.application.usecases;

import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.entity.OrderItem;
import com.nuan.storefront.domain.entity.Product;
import com.nuan.storefront.domain.port.OrderRepositoryPort;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CreateOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;

    public CreateOrderUseCase(OrderRepositoryPort orderRepository, ProductRepositoryPort productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public record ItemRequest(Long productId, int quantity) {}

    public Order execute(String customerEmail, List<ItemRequest> itemRequests) {
        log.info(""+1);
        Map<Long, Product> products = productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        log.info(""+2);
        List<OrderItem> items = itemRequests.stream().map(req -> {
            Product product = products.get(req.productId());
            if (product == null) {
                throw new IllegalArgumentException("Product not found: " + req.productId());
            }
            return new OrderItem(product.getId(), product.getName(), product.getPrice(), req.quantity());
        }).toList();
        log.info(""+3);

        Order order = new Order(null, customerEmail, items, Instant.now());
        log.info(""+4);
        return orderRepository.save(order);
    }
}
