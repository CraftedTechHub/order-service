package com.KalaroApplication.KALARO_ORDERS.dto.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MasterPlanSummary {
    private String color;
    private String size;
    private int orderQuantity;
    private List<MasterPlanSubDto> masterPlanSubDtos;

}
