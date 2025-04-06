package com.KalaroApplication.KALARO_ORDERS.dto;

import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSummaryDto {
    private String modelNo;
    private String modelName;
    private String yarnType;
    private String customerName;
    private String description;
    private String imageUrl;
    private List<MasterPlanSummary> masterPlanSummary;
}
