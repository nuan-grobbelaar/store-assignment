package com.nuan.storefront.domain.port;

import com.nuan.storefront.domain.entity.Order;

import java.util.List;

public interface OrderRepositoryPort {
    Order save(Order order);
    List<Order> findAll();
}
