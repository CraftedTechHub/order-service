package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;

import java.util.List;

public interface MasterPlanService {
    String saveOrderDetails(MasterPlanDto masterPlanDto);

    List<MasterPlanDto> getOrderDetails(int orderId);

    String updateOrderDetails(MasterPlanDto masterPlanDto);

    String deleteOrderDetails(int planId);

    List<MasterPlanDto> getMasterPlanSubByCenter(String centerName);

}
