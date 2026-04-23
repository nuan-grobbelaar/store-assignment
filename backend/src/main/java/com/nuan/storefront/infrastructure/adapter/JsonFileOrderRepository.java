package com.nuan.storefront.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuan.storefront.domain.entity.Order;
import com.nuan.storefront.domain.port.OrderRepositoryPort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

public class JsonFileOrderRepository implements OrderRepositoryPort {

    private final ObjectMapper objectMapper;
    private final File file;
    private final AtomicLong idSequence;

    public JsonFileOrderRepository(ObjectMapper objectMapper, String filePath) {
        this.objectMapper = objectMapper;
        this.file = new File(filePath);
        this.idSequence = new AtomicLong(nextId());
    }

    private long nextId() {
        List<Order> existing = readAll();
        return existing.stream().mapToLong(Order::getId).max().orElse(0) + 1;
    }

    private List<Order> readAll() {
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try {
            return new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read orders from " + file.getPath(), e);
        }
    }

    @Override
    public List<Order> findAll() {
        return readAll();
    }

    @Override
    public synchronized Order save(Order order) {
        List<Order> orders = readAll();
        Order persisted = new Order(idSequence.getAndIncrement(), order.getCustomerEmail(), order.getItems(), order.getCreatedAt());
        orders.add(persisted);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, orders);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write orders to " + file.getPath(), e);
        }
        return persisted;
    }
}
