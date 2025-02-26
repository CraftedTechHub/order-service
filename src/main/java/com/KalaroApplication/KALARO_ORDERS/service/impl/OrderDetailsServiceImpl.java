package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.EmpOrderDto;
import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import com.KalaroApplication.KALARO_ORDERS.repository.OrderDetailsRepository;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public int saveOrderDetails(OrderDetailsDto order) {
        OrderDetails orderDetailsList = orderDetailsRepository.findByModelNo(order.getModelNo());
        if(orderDetailsList!=null){
            log.error("Could not saved order details, you entered model number is exist");
            return 0;
        }else {
            OrderDetails orderDetails = new OrderDetails(
                    order.getOrderId(),
                    order.getModelNo(),
                    order.getModelName(),
                    order.getYarnType(),
                    order.getCustomerName(),
                    order.getSizeAndQuantity(),
                    order.getYarnWeight(),
                    order.getOrderWeight(),
                    order.getColor(),
                    order.getYarnImportDate(),
                    order.getCenterSampleApprovedDate(),
                    order.getYarnDistributionDate(),
                    order.getOrderCompletionDate(),
                    order.getDescription(),
                    order.getNote(),
                    order.getOrderCategory()
            );
            orderDetailsRepository.save(orderDetails);
            log.info("Order details saved successfully");
            return 1;
        }
    }

    @Override
    public OrderDetailsDto getOrderDetails(int orderId) {
        try{
            if(orderDetailsRepository.findByOrderId(orderId)==null){
                log.error("Order Id Not found");
                return null;
            }else{
                OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);

                OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                        orderDetails.getOrderId(),
                        orderDetails.getModelNo(),
                        orderDetails.getModelName(),
                        orderDetails.getYarnType(),
                        orderDetails.getCustomerName(),
                        orderDetails.getSizeAndQuantity(),
                        orderDetails.getYarnWeight(),
                        orderDetails.getOrderWeight(),
                        orderDetails.getColor(),
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
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching order details: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details. Please try again later.");
        }
    }

    @Override
    public int updateOrderDetails(OrderDetailsDto order) {
        try{
            OrderDetails orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrderIdNot(order.getOrderId());
            boolean state = false;
            for(OrderDetails orderDetails1 : orderDetailsList){
                if(orderDetails1.getModelNo().equals( order.getModelNo())){
                    state = true;
                    break;
                }
            }
            if(state) {
                log.error("Model number is already exist, Changes unsaved");
                return 0;
            }else{
                orderDetails.setModelNo(order.getModelNo());
                orderDetails.setModelName(order.getModelName());
                orderDetails.setYarnType(order.getYarnType());
                orderDetails.setSizeAndQuantity(order.getSizeAndQuantity());
                orderDetails.setYarnWeight(order.getYarnWeight());
                orderDetails.setOrderWeight(order.getOrderWeight());
                orderDetails.setColor(order.getColor());
                orderDetails.setYarnImportDate(order.getYarnImportDate());
                orderDetails.setCenterSampleApprovedDate(order.getCenterSampleApprovedDate());
                orderDetails.setYarnDistributionDate(order.getYarnDistributionDate());
                orderDetails.setOrderCompletionDate(order.getOrderCompletionDate());
                orderDetails.setDescription(order.getDescription());
                orderDetails.setNote(order.getNote());
                orderDetails.setOrderCategory(order.getOrderCategory());

                orderDetailsRepository.save(orderDetails);
                log.info("Order details updated successfully");
                return 1;
            }
        } catch (Exception e) {
            log.error("Error occurred while updating order details: {}", e.getMessage());
            throw new RuntimeException("Failed to update order details. Please try again later.");
        }
    }

    @Override
    public int deleteOrderDetails(int orderId) {
        try{
            if(orderDetailsRepository.findByOrderId(orderId) == null){
                log.error("Order details not found");
                return 0;
            }else{
                OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);
                orderDetailsRepository.delete(orderDetails);
                log.info("Order details deleted successfully");
                return 1;
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting order details: {}", e.getMessage());
            throw new RuntimeException("Failed to delete order details. Please try again later.");
        }

    }

    @Override
    public List<OrderDetailsDto> getAllOrderDetails() {
        try{
            if(orderDetailsRepository.findAll().isEmpty()){
                log.error("Not found order details");
                return null;
            }else{
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
                            orderDetails.getYarnWeight(),
                            orderDetails.getOrderWeight(),
                            orderDetails.getColor(),
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
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching all order details: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details. Please try again later.");
        }
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsByCategory(String category) {
        try{
            if(orderDetailsRepository.findAllByOrderCategory(category).isEmpty()){
                log.error("Not found order details");
                return null;
            }else{
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
                            orderDetails.getYarnWeight(),
                            orderDetails.getOrderWeight(),
                            orderDetails.getColor(),
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
                log.info("Order details fetched by category successfully");
                return orderDetailsDtoList;
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching order details by category: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch order details by category. Please try again later.");
        }
    }

    @Override
    public List<EmpOrderDto> getEmpOrders(String modelName) {
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByModelNameContainingIgnoreCase(modelName);
        List<EmpOrderDto> empOrderDtoList = new ArrayList<>();

        for (OrderDetails orderDetails : orderDetailsList) {
            EmpOrderDto empOrderDto = new EmpOrderDto();
            empOrderDto.setModelName(orderDetails.getModelName());

            // Extracting only the size from "Size:Quantity" format
            List<String> sizes = orderDetails.getSizeAndQuantity()
                    .stream()
                    .map(sizeQty -> sizeQty.split(":")[0]) // Extract size part
                    .collect(Collectors.toList());

            empOrderDto.setSizes(sizes);
            empOrderDtoList.add(empOrderDto);
        }
        return empOrderDtoList;
    }

}
