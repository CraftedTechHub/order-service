package com.KalaroApplication.KALARO_ORDERS.dto.component;


public class MasterPlanSubDto {
    private int id;
    private String center;
    private String date;
    private int qty;

    public MasterPlanSubDto() {
    }

    public MasterPlanSubDto(int id, String center, String date, int qty) {
        this.id = id;
        this.center = center;
        this.date = date;
        this.qty = qty;
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
}
