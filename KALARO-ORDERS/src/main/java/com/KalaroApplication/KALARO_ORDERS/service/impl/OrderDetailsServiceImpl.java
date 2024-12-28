package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import com.KalaroApplication.KALARO_ORDERS.repository.OrderDetailsRepository;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetailsDto saveOrderDetails(OrderDetailsDto order) {
        try{
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
            log.info("Order details saved successfully");

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
        } catch (Exception e) {
            log.error("Error occurred while saving order details: {}", e.getMessage());
            throw new RuntimeException("Failed to save order details. Please try again later.");
        }

    }

    @Override
    public OrderDetailsDto getOrderDetails(int orderId) {
        try{
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
            log.info("Order details fetched successfully");
            return orderDetailsDto;
        } catch (Exception e) {
            log.error("Error occurred while fetching order details: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details. Please try again later.");
        }
    }

    @Override
    public OrderDetailsDto updateOrderDetails(OrderDetailsDto order) {
        try{
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
            log.info("Order details updated successfully");

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
        } catch (Exception e) {
            log.error("Error occurred while updating order details: {}", e.getMessage());
            throw new RuntimeException("Failed to update order details. Please try again later.");
        }
    }

    @Override
    public String deleteOrderDetails(int orderId) {
        try{
            if(orderDetailsRepository.findByOrderId(orderId) == null){
                log.error("Order details not found");
                return "Order details not found";
            }else{
                OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);
                String message = orderDetails.getModelNo()+" "+orderDetails.getModelName() + " Order details deleted successfully";
                orderDetailsRepository.delete(orderDetails);
                log.info("Order details deleted successfully");
                return message;
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting order details: {}", e.getMessage());
            throw new RuntimeException("Failed to delete order details. Please try again later.");
        }

    }

    @Override
    public List<OrderDetailsDto> getAllOrderDetails() {
        try{
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
            log.info("All order details fetched successfully");
            return allOrderDetailsDtoList;
        } catch (Exception e) {
            log.error("Error occurred while fetching all order details: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details. Please try again later.");
        }
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsByCategory(String category) {
        try{
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
                log.info("Order details fetched by category successfully");
                orderDetailsDtoList.add(orderDetailsDto);
            }
            return orderDetailsDtoList;
        } catch (Exception e) {
            log.error("Error occurred while fetching order details by category: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details by category. Please try again later.");
        }
    }

    @Override
    public OrderDetailsDto passOrderDetails(int orderId) {
        try{
            OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);

            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
            orderDetailsDto.setOrderId(orderDetails.getOrderId());
            orderDetailsDto.setCustomerName(orderDetails.getCustomerName());
            orderDetailsDto.setYarnType(orderDetails.getYarnType());

            log.info("Order details passed successfully");
            return orderDetailsDto;
        } catch (Exception e) {
            log.error("Error occurred while passing order details: {}", e.getMessage());
            throw new RuntimeException("Failed to pass order details. Please try again later.");
        }
    }

}
