package com.KalaroApplication.KALARO_ORDERS.entity.component;

import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import jakarta.persistence.*;

@Entity
@Table(name="`Master_Plan_Sub`")
public class MasterPlanSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String center;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "master_plan_id", nullable = false)
    private MasterPlan masterPlan;

    public MasterPlanSub() {
    }

    public MasterPlanSub(int id, String center, String date, int qty, MasterPlan masterPlan) {
        this.id = id;
        this.center = center;
        this.date = date;
        this.qty = qty;
        this.masterPlan = masterPlan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public MasterPlan getMasterPlan() {
        return masterPlan;
    }

    public void setMasterPlan(MasterPlan masterPlan) {
        this.masterPlan = masterPlan;
    }
}
