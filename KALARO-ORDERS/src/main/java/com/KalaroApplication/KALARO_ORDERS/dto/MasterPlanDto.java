package com.KalaroApplication.KALARO_ORDERS.dto;

import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MasterPlanDto {
    private int planId;
    private int orderId;
    private String color;
    private String size;
    private int orderQuantity;
    private List<MasterPlanSubDto> subPlans;
}
