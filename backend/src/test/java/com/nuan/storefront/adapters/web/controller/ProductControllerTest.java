package com.nuan.storefront.adapters.web.controller;

import com.nuan.storefront.application.usecases.GetProductsUseCase;
import com.nuan.storefront.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductsUseCase getProductsUseCase;

    @InjectMocks
    private ProductController controller;

    private MockMvc mockMvc;

    private Product laptop;
    private Product phone;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        laptop = new Product(1L, "Laptop", "Fast laptop", 1500.0, "electronics", "Acme", 10, "img1");
        phone = new Product(2L, "Phone", "Smart phone", 800.0, "electronics", "Acme", 20, "img2");
    }

    @Test
    void getAllProducts_returnsList() throws Exception {
        when(getProductsUseCase.execute()).thenReturn(List.of(laptop, phone));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].price").value(1500.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Phone"));
    }

    @Test
    void getAllProducts_emptyList() throws Exception {
        when(getProductsUseCase.execute()).thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getProductsByCategory_returnsFilteredList() throws Exception {
        when(getProductsUseCase.executeByCategory("electronics")).thenReturn(List.of(laptop, phone));

        mockMvc.perform(get("/api/products/category/electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category").value("electronics"));
    }

    @Test
    void getProductById_returnsProduct_whenFound() throws Exception {
        when(getProductsUseCase.executeGetById(1L)).thenReturn(Optional.of(laptop));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.brand").value("Acme"))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void getProductById_returns404_whenNotFound() throws Exception {
        when(getProductsUseCase.executeGetById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found with id: 99"));
    }

    @Test
    void search_returnsMatches() throws Exception {
        when(getProductsUseCase.executeSearch("lap")).thenReturn(List.of(laptop));

        mockMvc.perform(get("/api/products/search/lap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }
}
