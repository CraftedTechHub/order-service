package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.EmpOrderDto;

import java.util.List;

public interface OrderDetailsService {
    int saveOrderDetails(OrderDetailsDto order);

    OrderDetailsDto getOrderDetails(int orderId);

    int updateOrderDetails(OrderDetailsDto order);

    int deleteOrderDetails(int orderId);

    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getOrderDetailsByCategory(String category);

    List<EmpOrderDto> getEmpOrders(String modelName);
}
