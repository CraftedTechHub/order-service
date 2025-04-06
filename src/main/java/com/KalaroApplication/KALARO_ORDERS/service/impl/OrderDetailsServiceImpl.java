package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.DOrdersDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderSummaryDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.*;
import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import com.KalaroApplication.KALARO_ORDERS.repository.MasterPlanRepository;
import com.KalaroApplication.KALARO_ORDERS.repository.MasterPlanSubRepository;
import com.KalaroApplication.KALARO_ORDERS.repository.OrderDetailsRepository;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private MasterPlanRepository masterPlanRepository;

    @Autowired
    private MasterPlanSubRepository masterPlanSubRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public int saveOrderDetails(OrderDetailsDto order, MultipartFile modelImage) throws IOException {

        OrderDetails existingOrder = orderDetailsRepository.findByModelNo(order.getModelNo());
        if (existingOrder != null) {
            log.error("Order details could not be saved, model number already exists: {}", order.getModelNo());
            return 0;
        }

        String modelImageUrl = null;
        if (modelImage != null && !modelImage.isEmpty()) {
            modelImageUrl = s3Service.uploadModelImage(modelImage, order.getModelNo());
        }

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
                modelImageUrl,
                order.getYarnImportDate(),
                order.getCenterSampleApprovedDate(),
                order.getYarnDistributionDate(),
                order.getOrderCompletionDate(),
                order.getDescription(),
                order.getNote(),
                order.getOrderCategory()
        );

        orderDetailsRepository.save(orderDetails);
        log.info("Order details saved successfully for Model No: {}", order.getModelNo());
        return 1;
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
                        orderDetails.getImageUrl(),
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
    public List<OrderDetailsDto> getOrderDetails() {
        try {
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
            List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();

            for (OrderDetails orderDetails : orderDetailsList) {
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
                        orderDetails.getImageUrl(),
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
        } catch (Exception e) {
            log.error("Error occurred while fetching all orders: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch orders. Please try again later.");
        }
    }


    @Override
    public int updateOrderDetails(OrderDetailsDto order, MultipartFile modelImage) {
        try {
            OrderDetails orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());

            if (orderDetails == null) {
                log.error("Order not found with ID: {}", order.getOrderId());
                return 0;
            }

            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrderIdNot(order.getOrderId());
            String previousImgUrl = order.getImageUrl();
            boolean state = orderDetailsList.stream()
                    .anyMatch(existingOrder -> existingOrder.getModelNo().equals(order.getModelNo()));

            if (state) {
                log.error("Model number already exists, changes unsaved");
                return 0;
            } else {
                String newModelImageUrl = previousImgUrl;

                if (modelImage != null && !modelImage.isEmpty()) {
                    try {
                        if (previousImgUrl != null && previousImgUrl.contains("/")) {
                            String previousFileName = previousImgUrl.substring(previousImgUrl.lastIndexOf("/") + 1);
                            s3Service.deleteModelImage(previousFileName);
                        }
                        newModelImageUrl = s3Service.uploadModelImage(modelImage, order.getModelNo());
                    } catch (Exception e) {
                        log.error("Error handling model image: {}", e.getMessage());
                        throw new RuntimeException("Failed to update model image.");
                    }
                }

                orderDetails.setModelNo(order.getModelNo());
                orderDetails.setModelName(order.getModelName());
                orderDetails.setYarnType(order.getYarnType());
                orderDetails.setSizeAndQuantity(order.getSizeAndQuantity());
                orderDetails.setYarnWeight(order.getYarnWeight());
                orderDetails.setOrderWeight(order.getOrderWeight());
                orderDetails.setColor(order.getColor());
                orderDetails.setImageUrl(newModelImageUrl);
                orderDetails.setYarnImportDate(order.getYarnImportDate());
                orderDetails.setCenterSampleApprovedDate(order.getCenterSampleApprovedDate());
                orderDetails.setYarnDistributionDate(order.getYarnDistributionDate());
                orderDetails.setOrderCompletionDate(order.getOrderCompletionDate());
                orderDetails.setDescription(order.getDescription());
                orderDetails.setNote(order.getNote());
                orderDetails.setOrderCategory(order.getOrderCategory());

                orderDetailsRepository.save(orderDetails);
                log.info("Order details updated successfully for ID: {}", order.getOrderId());
                return 1;
            }
        } catch (Exception e) {
            log.error("Error occurred while updating order details: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update order details. Please try again later.");
        }
    }

    @Override
    public int deleteOrderDetails(int orderId) {
        try {
            OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);

            if (orderDetails == null) {
                log.error("Order details not found for ID: {}", orderId);
                return 0;
            }

            List<MasterPlan> masterPlanList = masterPlanRepository.findAllByOrderId(orderId);
            if (!masterPlanList.isEmpty()) {
                masterPlanRepository.deleteAll(masterPlanList);
            }

            String imageUrl = orderDetails.getImageUrl();
            if (imageUrl != null && imageUrl.contains("/")) {
                try {
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    s3Service.deleteModelImage(fileName);
                } catch (Exception e) {
                    log.error("Failed to delete model image for Order ID {}: {}", orderId, e.getMessage());
                    throw new RuntimeException("Failed to delete associated image.");
                }
            }

            orderDetailsRepository.delete(orderDetails);
            log.info("Order details deleted successfully for ID: {}", orderId);
            return 1;

        } catch (Exception e) {
            log.error("Error occurred while deleting order details for ID {}: {}", orderId, e.getMessage(), e);
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
                            orderDetails.getImageUrl(),
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
                            orderDetails.getImageUrl(),
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

    @Override
    public List<DOrdersDto> getOrdersForDashboard() {
        try {
            String category = "Ongoing orders";
            List<OrderDetails> allOrderDetails = orderDetailsRepository.findAllByOrderCategory(category);

            if (allOrderDetails.isEmpty()) {
                log.error("Not found order details");
                return List.of();
            }

            List<DOrdersDto> dOrdersDtoList = new ArrayList<>();
            for (OrderDetails orderDetails : allOrderDetails) {
                DOrdersDto dOrderDto = new DOrdersDto();
                dOrderDto.setOrder(orderDetails.getModelName()); // ✅ Matches JSON field "order"

                // Fetch plan IDs in one go to reduce queries
                List<MasterPlan> masterPlanList = masterPlanRepository.findAllByOrderId(orderDetails.getOrderId());
//                List<Integer> planIds = masterPlanList.stream()
//                        .map(MasterPlan::getPlanId)
//                        .collect(Collectors.toList());

                Set<String> uniqueCenters = masterPlanList.stream()
                        .flatMap(masterPlan -> masterPlanSubRepository.findAllCentersByMasterPlan(masterPlan).stream())
                        .collect(Collectors.toSet());


                List<DDataDto> dDataDtoList = new ArrayList<>();
                for (String center : uniqueCenters) {
                    // Fetch masterPlanSubs in one go for this center
                    List<MasterPlanSub> masterPlanSubList = masterPlanSubRepository.findAllByCenter(center).stream()
                            .filter(masterPlanSub -> Objects.equals(masterPlanSub.getMasterPlan().getOrderId(), orderDetails.getOrderId()))
                            .collect(Collectors.toList());

                    DDataDto dDataDto = new DDataDto();
                    try {
                        dDataDto.setStartDate(orderDetails.getYarnDistributionDate());
                        dDataDto.setExpectedCompletionDate(orderDetails.getOrderCompletionDate());
                    } catch (DateTimeParseException e) {
                        log.error("Error parsing date for order {}", orderDetails.getOrderId(), e);
                        dDataDto.setStartDate(null);
                        dDataDto.setExpectedCompletionDate(null);
                    }

                    dDataDto.setCenterName(center);

                    // Convert to SizeAndQuantityDto
                    List<SizeAndQuantityDto> sizeAndQuantityDtoList = masterPlanSubList.stream()
                            .map(masterPlanSub -> new SizeAndQuantityDto(
                                    masterPlanSub.getMasterPlan().getSize(),
                                    masterPlanSub.getQty()))
                            .collect(Collectors.toList());

                    dDataDto.setSizeQuantities(sizeAndQuantityDtoList);
                    dDataDtoList.add(dDataDto);
                }

                dOrderDto.setData(dDataDtoList);
                dOrdersDtoList.add(dOrderDto);
            }

            log.info("All order details fetched successfully");
            return dOrdersDtoList;

        } catch (Exception e) {
            log.error("Error fetching order details", e);
            throw new RuntimeException("Failed to fetch orders", e);
        }
    }

    @Override
    public int getOrdersQtyForEachCenter(String centerName) {
        List<MasterPlanSub> masterPlanSubList = masterPlanSubRepository.findAllByCenter(centerName);
        Set<Integer> orderIds = masterPlanSubList.stream()
                .map(mps -> mps.getMasterPlan().getOrderId())
                .collect(Collectors.toSet());
        return orderIds.size();
    }

    @Override
    public OrderSummaryDto getOrderSammary(int orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(orderId);
        if (orderDetails == null) {
            log.error("Order details not found for ID: {}", orderId);
            return null;
        }

        List<MasterPlan> masterPlanList = masterPlanRepository.findAllByOrderId(orderId);
        //List<MasterPlanSub> masterPlanSubList = masterPlanSubRepository.findAllByMasterPlanIn(masterPlanList);
        OrderSummaryDto orderSummaryDto = new OrderSummaryDto();
        orderSummaryDto.setModelNo(orderDetails.getModelNo());
        orderSummaryDto.setModelName(orderDetails.getModelName());
        orderSummaryDto.setYarnType(orderDetails.getYarnType());
        orderSummaryDto.setCustomerName(orderDetails.getCustomerName());
        orderSummaryDto.setDescription(orderDetails.getDescription());
        orderSummaryDto.setImageUrl(orderDetails.getImageUrl());

        List<MasterPlanSummary> masterPlanSummaryList = new ArrayList<>();
        for (MasterPlan masterPlan : masterPlanList) {
            MasterPlanSummary masterPlanSummary = new MasterPlanSummary();
            masterPlanSummary.setColor(masterPlan.getColor());
            masterPlanSummary.setSize(masterPlan.getSize());
            masterPlanSummary.setOrderQuantity(masterPlan.getOrderQuantity());

            List<MasterPlanSubDto> masterPlanSubDtoList = new ArrayList<>();
            for (MasterPlanSub masterPlanSub : masterPlan.getSubPlans()) {
                    MasterPlanSubDto masterPlanSubDto = new MasterPlanSubDto();
                    masterPlanSubDto.setCenter(masterPlanSub.getCenter());
                    masterPlanSubDto.setDate(masterPlanSub.getDate());
                    masterPlanSubDto.setQty(masterPlanSub.getQty());
                    masterPlanSubDtoList.add(masterPlanSubDto);
            }
            masterPlanSummary.setMasterPlanSubDtos(masterPlanSubDtoList);
            masterPlanSummaryList.add(masterPlanSummary);
        }
        orderSummaryDto.setMasterPlanSummary(masterPlanSummaryList);
        return orderSummaryDto;
    }

}
