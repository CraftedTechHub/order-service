package com.KalaroApplication.KALARO_ORDERS.dto.component;


import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MasterPlanSubDto {
    private int id;
    private String center;
    private String date;
    private int qty;
    private MasterPlanDto masterPlan;
}
