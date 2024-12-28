package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;

import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDto saveOrderDetails(OrderDetailsDto order);
    OrderDetailsDto getOrderDetails(int orderId);
    OrderDetailsDto updateOrderDetails(OrderDetailsDto order);
    String deleteOrderDetails(int orderId);

    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getOrderDetailsByCategory(String category);

    OrderDetailsDto passOrderDetails(int orderId);
}
