package com.nuan.storefront.adapters.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
