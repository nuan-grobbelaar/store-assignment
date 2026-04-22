package com.nuan.storefront.adapters.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String customerEmail;
    private List<OrderItemDTO> items;
    private Double total;
    private Instant createdAt;
}
