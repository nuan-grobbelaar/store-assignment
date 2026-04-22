package com.nuan.storefront.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;

    public Double subtotal() {
        return unitPrice * quantity;
    }
}
