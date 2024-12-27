package com.KalaroApplication.KALARO_ORDERS.dto;

import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;

import java.util.List;

public class MasterPlanDto {
    private int planId;
    private int orderId;
    private String color;
    private String size;
    private int orderQuantity;
    private List<MasterPlanSubDto> subPlans;

    public MasterPlanDto() {
    }

    public MasterPlanDto(int planId, int orderId, String color, String size, int orderQuantity, List<MasterPlanSubDto> subPlans) {
        this.planId = planId;
        this.orderId = orderId;
        this.color = color;
        this.size = size;
        this.orderQuantity = orderQuantity;
        this.subPlans = subPlans;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public List<MasterPlanSubDto> getSubPlans() {
        return subPlans;
    }

    public void setSubPlans(List<MasterPlanSubDto> subPlans) {
        this.subPlans = subPlans;
    }
}
