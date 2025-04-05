package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.DOrdersDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.EmpOrderDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrderDetailsService {
    int saveOrderDetails(OrderDetailsDto order, MultipartFile modelImage) throws IOException;

    OrderDetailsDto getOrderDetails(int orderId);

    int updateOrderDetails(OrderDetailsDto order, MultipartFile modelImage);

    int deleteOrderDetails(int orderId);

    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getOrderDetailsByCategory(String category);

    List<EmpOrderDto> getEmpOrders(String modelName);

    List<DOrdersDto> getOrdersForDashboard();

    int getOrdersQtyForEachCenter(String centerName);

    List<OrderDetailsDto> getOrderDetails();
}
