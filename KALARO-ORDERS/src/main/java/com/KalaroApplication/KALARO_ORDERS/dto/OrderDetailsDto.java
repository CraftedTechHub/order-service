package com.KalaroApplication.KALARO_ORDERS.dto;

import java.util.List;

public class OrderDetailsDto {

    private int orderId;
    private String modelNo;
    private String modelName;
    private String yarnType;
    private String customerName;
    private List<String> sizeAndQuantity;
    private List<String> colors;
    private String yarnImportDate;
    private String centerSampleApprovedDate;
    private String yarnDistributionDate;
    private String orderCompletionDate;
    private String description;
    private String note;
    private String orderCategory = "Ongoing orders";

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(int orderId, String modelNo, String modelName, String yarnType, String customerName, List<String> sizeAndQuantity, List<String> colors, String yarnImportDate, String centerSampleApprovedDate, String yarnDistributionDate, String orderCompletionDate, String description, String note, String orderCategory) {
        this.orderId = orderId;
        this.modelNo = modelNo;
        this.modelName = modelName;
        this.yarnType = yarnType;
        this.customerName = customerName;
        this.sizeAndQuantity = sizeAndQuantity;
        this.colors = colors;
        this.yarnImportDate = yarnImportDate;
        this.centerSampleApprovedDate = centerSampleApprovedDate;
        this.yarnDistributionDate = yarnDistributionDate;
        this.orderCompletionDate = orderCompletionDate;
        this.description = description;
        this.note = note;
        this.orderCategory = orderCategory;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getYarnType() {
        return yarnType;
    }

    public void setYarnType(String yarnType) {
        this.yarnType = yarnType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getSizeAndQuantity() {
        return sizeAndQuantity;
    }

    public void setSizeAndQuantity(List<String> sizeAndQuantity) {
        this.sizeAndQuantity = sizeAndQuantity;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getYarnImportDate() {
        return yarnImportDate;
    }

    public void setYarnImportDate(String yarnImportDate) {
        this.yarnImportDate = yarnImportDate;
    }

    public String getCenterSampleApprovedDate() {
        return centerSampleApprovedDate;
    }

    public void setCenterSampleApprovedDate(String centerSampleApprovedDate) {
        this.centerSampleApprovedDate = centerSampleApprovedDate;
    }

    public String getYarnDistributionDate() {
        return yarnDistributionDate;
    }

    public void setYarnDistributionDate(String yarnDistributionDate) {
        this.yarnDistributionDate = yarnDistributionDate;
    }

    public String getOrderCompletionDate() {
        return orderCompletionDate;
    }

    public void setOrderCompletionDate(String orderCompletionDate) {
        this.orderCompletionDate = orderCompletionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    @Override
    public String toString() {
        return "OrderDetailsDto{" +
                "orderId=" + orderId +
                ", modelNo='" + modelNo + '\'' +
                ", modelName='" + modelName + '\'' +
                ", yarnType='" + yarnType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", sizeAndQuantity=" + sizeAndQuantity +
                ", colors=" + colors +
                ", yarnImportDate='" + yarnImportDate + '\'' +
                ", centerSampleApprovedDate='" + centerSampleApprovedDate + '\'' +
                ", yarnDistributionDate='" + yarnDistributionDate + '\'' +
                ", orderCompletionDate='" + orderCompletionDate + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", orderCategory='" + orderCategory + '\'' +
                '}';
    }
}
