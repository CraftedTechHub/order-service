package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;

import java.util.List;

public interface OrderDetailsService {
    int saveOrderDetails(OrderDetailsDto order);

    OrderDetailsDto getOrderDetails(int orderId);

    int updateOrderDetails(OrderDetailsDto order);

    int deleteOrderDetails(int orderId);

    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getOrderDetailsByCategory(String category);

    OrderDetailsDto passOrderDetails(int orderId);
}
