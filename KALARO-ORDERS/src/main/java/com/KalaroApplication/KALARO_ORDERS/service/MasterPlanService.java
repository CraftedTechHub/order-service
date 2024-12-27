package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;

import java.util.List;

public interface MasterPlanService {
    String saveOrderDetails(MasterPlanDto masterPlanDto);

    List<MasterPlanDto> getOrderDetails(int orderId);

    String updateOrderDetails(MasterPlanDto masterPlanDto);

    String deleteOrderDetails(int planId);
}
