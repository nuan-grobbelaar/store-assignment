package com.nuan.storefront.adapters.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nuan.storefront.adapters.web.dto.OrderItemDTO;
import com.nuan.storefront.adapters.web.dto.OrderRequestDTO;
import com.nuan.storefront.application.usecases.CreateOrderUseCase;
import com.nuan.storefront.application.usecases.GetOrdersUseCase;
import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.entity.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private GetOrdersUseCase getOrdersUseCase;

    @InjectMocks
    private OrderController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getOrders_returnsList() throws Exception {
        Order order = new Order(10L, "buyer@example.com",
                List.of(new OrderItem(1L, "Laptop", 1500.0, 2)),
                Instant.parse("2026-04-22T10:00:00Z"));

        when(getOrdersUseCase.execute()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].customerEmail").value("buyer@example.com"))
                .andExpect(jsonPath("$[0].total").value(3000.0))
                .andExpect(jsonPath("$[0].items", hasSize(1)))
                .andExpect(jsonPath("$[0].items[0].productId").value(1))
                .andExpect(jsonPath("$[0].items[0].quantity").value(2));
    }

    @Test
    void createOrder_returns201_whenValid() throws Exception {
        Order savedOrder = new Order(77L, "buyer@example.com",
                List.of(new OrderItem(1L, "Laptop", 1500.0, 2)),
                Instant.parse("2026-04-22T10:00:00Z"));

        when(createOrderUseCase.execute(anyString(), any())).thenReturn(savedOrder);

        OrderRequestDTO req = new OrderRequestDTO("buyer@example.com",
                List.of(new OrderItemDTO(1L, "Laptop", 2)));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(77))
                .andExpect(jsonPath("$.customerEmail").value("buyer@example.com"))
                .andExpect(jsonPath("$.total").value(3000.0))
                .andExpect(jsonPath("$.items[0].productId").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void createOrder_returns400_whenUseCaseThrows() throws Exception {
        when(createOrderUseCase.execute(anyString(), any()))
                .thenThrow(new IllegalArgumentException("Product not found: 999"));

        OrderRequestDTO req = new OrderRequestDTO("buyer@example.com",
                List.of(new OrderItemDTO(999L, "", 1)));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product not found: 999"));
    }
}
