package com.nuan.storefront.application.usecases;

import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.entity.OrderItem;
import com.nuan.storefront.domain.entity.Product;
import com.nuan.storefront.domain.port.OrderRepositoryPort;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private CreateOrderUseCase useCase;

    private Product laptop;
    private Product phone;

    @BeforeEach
    void setUp() {
        laptop = new Product(1L, "Laptop", "Fast laptop", 1500.0, "electronics", "Acme", 10, "img1");
        phone = new Product(2L, "Phone", "Smart phone", 800.0, "electronics", "Acme", 20, "img2");
    }

    @Test
    void execute_createsOrderWithItemsAndSaves() {
        when(productRepository.findAll()).thenReturn(List.of(laptop, phone));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(42L);
            return o;
        });

        Order result = useCase.execute("buyer@example.com", List.of(
                new CreateOrderUseCase.ItemRequest(1L, 2),
                new CreateOrderUseCase.ItemRequest(2L, 1)
        ));

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order saved = captor.getValue();

        assertThat(saved.getCustomerEmail()).isEqualTo("buyer@example.com");
        assertThat(saved.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(saved.getItems()).hasSize(2);

        OrderItem first = saved.getItems().get(0);
        assertThat(first.getProductId()).isEqualTo(1L);
        assertThat(first.getProductName()).isEqualTo("Laptop");
        assertThat(first.getUnitPrice()).isEqualTo(1500.0);
        assertThat(first.getQuantity()).isEqualTo(2);

        OrderItem second = saved.getItems().get(1);
        assertThat(second.getProductId()).isEqualTo(2L);
        assertThat(second.getProductName()).isEqualTo("Phone");
        assertThat(second.getUnitPrice()).isEqualTo(800.0);
        assertThat(second.getQuantity()).isEqualTo(1);

        assertThat(result.getId()).isEqualTo(42L);
        assertThat(result.total()).isEqualTo(1500.0 * 2 + 800.0);
    }

    @Test
    void execute_throwsWhenProductNotFound() {
        when(productRepository.findAll()).thenReturn(List.of(laptop));

        assertThatThrownBy(() -> useCase.execute("buyer@example.com", List.of(
                new CreateOrderUseCase.ItemRequest(999L, 1)
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product not found: 999");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void execute_emptyItemList_savesEmptyOrder() {
        when(productRepository.findAll()).thenReturn(List.of(laptop));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order result = useCase.execute("buyer@example.com", List.of());

        assertThat(result.getItems()).isEmpty();
        assertThat(result.total()).isZero();
        verify(orderRepository).save(any(Order.class));
    }
}
