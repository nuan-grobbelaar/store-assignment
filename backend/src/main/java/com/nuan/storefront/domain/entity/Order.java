package com.nuan.storefront.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String customerEmail;
    private List<OrderItem> items;
    private Instant createdAt;

    public Double total() {
        return items.stream().mapToDouble(OrderItem::subtotal).sum();
    }
}
