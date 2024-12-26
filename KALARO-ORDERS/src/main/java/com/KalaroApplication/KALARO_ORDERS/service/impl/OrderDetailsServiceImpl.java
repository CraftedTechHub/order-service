package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import com.KalaroApplication.KALARO_ORDERS.repository.OrderDetailsRepository;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetailsDto saveOrderDetails(OrderDetailsDto order) {
        OrderDetails orderDetails = new OrderDetails(
                order.getOrderId(),
                order.getModelNo(),
                order.getModelName(),
                order.getYarnType(),
                order.getCustomerName(),
                order.getSizeAndQuantity(),
                order.getColors(),
                order.getYarnImportDate(),
                order.getCenterSampleApprovedDate(),
                order.getYarnDistributionDate(),
                order.getOrderCompletionDate(),
                order.getDescription(),
                order.getNote(),
                order.getOrderCategory()
        );

        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                savedOrderDetails.getOrderId(),
                savedOrderDetails.getModelNo(),
                savedOrderDetails.getModelName(),
                savedOrderDetails.getYarnType(),
                savedOrderDetails.getCustomerName(),
                savedOrderDetails.getSizeAndQuantity(),
                savedOrderDetails.getColors(),
                savedOrderDetails.getYarnImportDate(),
                savedOrderDetails.getCenterSampleApprovedDate(),
                savedOrderDetails.getYarnDistributionDate(),
                savedOrderDetails.getOrderCompletionDate(),
                savedOrderDetails.getDescription(),
                savedOrderDetails.getNote(),
                savedOrderDetails.getOrderCategory()
        );
        return orderDetailsDto;
    }

    @Override
    public OrderDetailsDto getOrderDetails(int orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                orderDetails.getOrderId(),
                orderDetails.getModelNo(),
                orderDetails.getModelName(),
                orderDetails.getYarnType(),
                orderDetails.getCustomerName(),
                orderDetails.getSizeAndQuantity(),
                orderDetails.getColors(),
                orderDetails.getYarnImportDate(),
                orderDetails.getCenterSampleApprovedDate(),
                orderDetails.getYarnDistributionDate(),
                orderDetails.getOrderCompletionDate(),
                orderDetails.getDescription(),
                orderDetails.getNote(),
                orderDetails.getOrderCategory()
        );
        return orderDetailsDto;
    }

    @Override
    public OrderDetailsDto updateOrderDetails(OrderDetailsDto order) {

        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());

        orderDetails.setModelNo(order.getModelNo());
        orderDetails.setModelName(order.getModelName());
        orderDetails.setYarnType(order.getYarnType());
        orderDetails.setSizeAndQuantity(order.getSizeAndQuantity());
        orderDetails.setColors(order.getColors());
        orderDetails.setYarnImportDate(order.getYarnImportDate());
        orderDetails.setCenterSampleApprovedDate(order.getCenterSampleApprovedDate());
        orderDetails.setYarnDistributionDate(order.getYarnDistributionDate());
        orderDetails.setOrderCompletionDate(order.getOrderCompletionDate());
        orderDetails.setDescription(order.getDescription());
        orderDetails.setNote(order.getNote());
        orderDetails.setOrderCategory(order.getOrderCategory());

        OrderDetails updatedOrderDetails = orderDetailsRepository.save(orderDetails);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                updatedOrderDetails.getOrderId(),
                updatedOrderDetails.getModelNo(),
                updatedOrderDetails.getModelName(),
                updatedOrderDetails.getYarnType(),
                updatedOrderDetails.getCustomerName(),
                updatedOrderDetails.getSizeAndQuantity(),
                updatedOrderDetails.getColors(),
                updatedOrderDetails.getYarnImportDate(),
                updatedOrderDetails.getCenterSampleApprovedDate(),
                updatedOrderDetails.getYarnDistributionDate(),
                updatedOrderDetails.getOrderCompletionDate(),
                updatedOrderDetails.getDescription(),
                updatedOrderDetails.getNote(),
                updatedOrderDetails.getOrderCategory()
        );
        return orderDetailsDto;
    }

    @Override
    public void deleteOrderDetails(int orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);
        orderDetailsRepository.delete(orderDetails);
    }

    @Override
    public List<OrderDetailsDto> getAllOrderDetails() {
        List<OrderDetails> allOrderDetails = orderDetailsRepository.findAll();
        List<OrderDetailsDto> allOrderDetailsDtoList = new ArrayList<>();

        for(OrderDetails orderDetails : allOrderDetails){
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderDetails.getOrderId(),
                    orderDetails.getModelNo(),
                    orderDetails.getModelName(),
                    orderDetails.getYarnType(),
                    orderDetails.getCustomerName(),
                    orderDetails.getSizeAndQuantity(),
                    orderDetails.getColors(),
                    orderDetails.getYarnImportDate(),
                    orderDetails.getCenterSampleApprovedDate(),
                    orderDetails.getYarnDistributionDate(),
                    orderDetails.getOrderCompletionDate(),
                    orderDetails.getDescription(),
                    orderDetails.getNote(),
                    orderDetails.getOrderCategory()
            );
            allOrderDetailsDtoList.add(orderDetailsDto);
        }
        return allOrderDetailsDtoList;
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsByCategory(String category) {
        List<OrderDetails> allOngoingOrderDetails = orderDetailsRepository.findAllByOrderCategory(category);
        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();

        for(OrderDetails orderDetails : allOngoingOrderDetails){
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderDetails.getOrderId(),
                    orderDetails.getModelNo(),
                    orderDetails.getModelName(),
                    orderDetails.getYarnType(),
                    orderDetails.getCustomerName(),
                    orderDetails.getSizeAndQuantity(),
                    orderDetails.getColors(),
                    orderDetails.getYarnImportDate(),
                    orderDetails.getCenterSampleApprovedDate(),
                    orderDetails.getYarnDistributionDate(),
                    orderDetails.getOrderCompletionDate(),
                    orderDetails.getDescription(),
                    orderDetails.getNote(),
                    orderDetails.getOrderCategory()
            );
            orderDetailsDtoList.add(orderDetailsDto);
        }
        return orderDetailsDtoList;
    }

    @Override
    public OrderDetailsDto passOrderDetails(int orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setOrderId(orderDetails.getOrderId());
        orderDetailsDto.setCustomerName(orderDetails.getCustomerName());
        orderDetailsDto.setYarnType(orderDetails.getYarnType());

        return orderDetailsDto;
    }

}
