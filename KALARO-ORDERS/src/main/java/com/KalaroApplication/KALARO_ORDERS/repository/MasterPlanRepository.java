package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterPlanRepository {
    MasterPlan findMasterPlan(int orderId);
}
