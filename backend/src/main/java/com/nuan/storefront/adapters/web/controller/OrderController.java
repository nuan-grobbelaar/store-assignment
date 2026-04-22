package com.nuan.storefront.adapters.web.controller;

import com.nuan.storefront.adapters.web.dto.OrderItemDTO;
import com.nuan.storefront.adapters.web.dto.OrderRequestDTO;
import com.nuan.storefront.adapters.web.dto.OrderResponseDTO;
import com.nuan.storefront.application.usecases.CreateOrderUseCase;
import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.port.OrderRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRepositoryPort orderRepository;

    public OrderController(CreateOrderUseCase createOrderUseCase, OrderRepositoryPort orderRepository) {
        this.createOrderUseCase = createOrderUseCase;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<OrderResponseDTO> getOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        List<CreateOrderUseCase.ItemRequest> itemRequests = request.getItems().stream()
                .map(i -> new CreateOrderUseCase.ItemRequest(i.getProductId(), i.getQuantity()))
                .toList();

        try {
            Order order = createOrderUseCase.execute(request.getCustomerEmail(), itemRequests);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private OrderResponseDTO toResponse(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(i -> new OrderItemDTO(i.getProductId(), i.getQuantity()))
                .toList();
        return new OrderResponseDTO(order.getId(), order.getCustomerEmail(), itemDTOs, order.total(), order.getCreatedAt());
    }
}
