package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterPlanRepository extends JpaRepository<MasterPlan,Integer>{
    List<MasterPlan> findAllByOrderId(int orderId);
    void deleteByOrderId(int orderId);
}
