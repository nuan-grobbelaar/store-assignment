package com.nuan.storefront.application.usecases;

import com.nuan.storefront.domain.entity.Product;
import com.nuan.storefront.domain.port.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductsUseCaseTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private GetProductsUseCase useCase;

    private Product laptop;
    private Product phone;

    @BeforeEach
    void setUp() {
        laptop = new Product(1L, "Laptop", "Fast laptop", 1500.0, "electronics", "Acme", 10, "img1");
        phone = new Product(2L, "Phone", "Smart phone", 800.0, "electronics", "Acme", 20, "img2");
    }

    @Test
    void execute_returnsAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(laptop, phone));

        List<Product> result = useCase.execute();

        assertThat(result).containsExactly(laptop, phone);
        verify(productRepository).findAll();
    }

    @Test
    void execute_returnsEmptyList_whenRepoEmpty() {
        when(productRepository.findAll()).thenReturn(List.of());

        assertThat(useCase.execute()).isEmpty();
    }

    @Test
    void executeByCategory_delegatesToRepo() {
        when(productRepository.findByCategory("electronics")).thenReturn(List.of(laptop, phone));

        List<Product> result = useCase.executeByCategory("electronics");

        assertThat(result).containsExactly(laptop, phone);
        verify(productRepository).findByCategory("electronics");
    }

    @Test
    void executeSearch_withValidQuery_callsSearch() {
        when(productRepository.search("laptop")).thenReturn(List.of(laptop));

        List<Product> result = useCase.executeSearch("laptop");

        assertThat(result).containsExactly(laptop);
        verify(productRepository).search("laptop");
        verify(productRepository, never()).findAll();
    }

    @Test
    void executeSearch_withNullQuery_returnsFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(laptop, phone));

        List<Product> result = useCase.executeSearch(null);

        assertThat(result).containsExactly(laptop, phone);
        verify(productRepository).findAll();
        verify(productRepository, never()).search(anyString());
    }

    @Test
    void executeSearch_withEmptyQuery_returnsFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(laptop));

        List<Product> result = useCase.executeSearch("");

        assertThat(result).containsExactly(laptop);
        verify(productRepository).findAll();
        verify(productRepository, never()).search(anyString());
    }

    @Test
    void executeSearch_withWhitespaceQuery_returnsFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(laptop));

        useCase.executeSearch("   ");

        verify(productRepository).findAll();
        verify(productRepository, never()).search(anyString());
    }

    @Test
    void executeGetById_returnsProduct_whenFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(laptop));

        Optional<Product> result = useCase.executeGetById(1L);

        assertThat(result).contains(laptop);
    }

    @Test
    void executeGetById_returnsEmpty_whenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(useCase.executeGetById(99L)).isEmpty();
    }
}
