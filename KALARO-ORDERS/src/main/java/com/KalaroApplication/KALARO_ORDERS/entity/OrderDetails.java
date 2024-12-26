package com.KalaroApplication.KALARO_ORDERS.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`Customer_order`")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String modelNo;
    private String modelName;

    @Column(nullable = false)
    private String yarnType;

    @Column(nullable = false)
    private String customerName;

    @ElementCollection
    @CollectionTable(name = "order_size_quantity", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "size_and_quantity")
    private List<String> sizeAndQuantity;

    @ElementCollection
    @CollectionTable(name = "order_colors", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "color")
    private List<String> colors;

    @Column(name = "Yarn Imported Date")
    private String yarnImportDate;

    @Column(name = "Center Sample Approved Date")
    private String centerSampleApprovedDate;

    @Column(name = "Yarn Distribution Date")
    private String yarnDistributionDate;

    @Column(name = "Order Completion Date")
    private String orderCompletionDate;

    private String description;
    private String note;

    @Column(nullable = false)
    private String orderCategory = "Ongoing";

    public OrderDetails() {
    }

    public OrderDetails(int orderId, String modelNo, String modelName, String yarnType, String customerName, List<String> sizeAndQuantity, List<String> colors, String yarnImportDate, String centerSampleApprovedDate, String yarnDistributionDate, String orderCompletionDate, String description, String note, String orderCategory) {
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
}
