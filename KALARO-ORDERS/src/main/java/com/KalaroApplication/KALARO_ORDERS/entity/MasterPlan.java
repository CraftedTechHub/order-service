package com.KalaroApplication.KALARO_ORDERS.entity;

import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`Master_Plan_Main`")
public class MasterPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;

    @Column(nullable = false)
    private int orderId;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private int orderQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_master_planId",referencedColumnName = "planId")
    private List<MasterPlanSub> subPlans;

    public MasterPlan() {
    }

    public MasterPlan(int planId, int orderId, String color, String size, int orderQuantity, List<MasterPlanSub> subPlans) {
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

    public List<MasterPlanSub> getSubPlans() {
        return subPlans;
    }

    public void setSubPlans(List<MasterPlanSub> subPlans) {
        this.subPlans = subPlans;
    }

    @Override
    public String toString() {
        return "MasterPlan{" +
                "planId=" + planId +
                ", orderId=" + orderId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", subPlans=" + subPlans +
                '}';
    }
}
