package com.KalaroApplication.KALARO_ORDERS.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDto {

    private int orderId;
    private String modelNo;
    private String modelName;
    private String yarnType;
    private String customerName;
    private List<String> sizeAndQuantity;
    private double yarnWeight;
    private double orderWeight;
    private String color;
    private String yarnImportDate;
    private String centerSampleApprovedDate;
    private String yarnDistributionDate;
    private String orderCompletionDate;
    private String description;
    private String note;
    private String orderCategory = "Ongoing orders";
}
