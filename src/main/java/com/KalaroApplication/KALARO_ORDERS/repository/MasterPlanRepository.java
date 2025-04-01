package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MasterPlanRepository extends JpaRepository<MasterPlan,Integer>{
    List<MasterPlan> findAllByOrderId(int orderId);
}
