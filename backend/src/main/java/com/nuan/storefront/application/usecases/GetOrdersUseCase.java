package com.nuan.storefront.application.usecases;

import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.port.OrderRepositoryPort;

import java.util.List;

public class GetOrdersUseCase {
    private final OrderRepositoryPort orderRepository;

    public GetOrdersUseCase(OrderRepositoryPort orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> execute() {
        return orderRepository.findAll();
    }
}
