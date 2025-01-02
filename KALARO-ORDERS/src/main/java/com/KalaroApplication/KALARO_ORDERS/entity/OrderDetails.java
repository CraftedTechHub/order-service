package com.KalaroApplication.KALARO_ORDERS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "`Customer_order`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

    @Column(name = "color")
    private String color;
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
}
