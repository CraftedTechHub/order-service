package com.KalaroApplication.KALARO_ORDERS.service;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;

import java.util.List;

public interface MasterPlanService {
    int saveOrderDetails(MasterPlanDto masterPlanDto);

    List<MasterPlanDto> getOrderDetails(int orderId);

    int updateOrderDetails(MasterPlanDto masterPlanDto);

    int deleteOrderDetails(int planId);

    MasterPlanDto getMasterPlan(int planId);

    MasterPlanDto getMasterPlanDetailsForCenter(int orderId, String centerName);

    List<MasterPlanDto> getMasterPlanSubByCenter(String centerName);
}
