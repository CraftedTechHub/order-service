package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MasterPlanSubRepository extends JpaRepository<MasterPlanSub,Integer> {

    List<MasterPlanSub> findAllByCenter(String center);

    @Query("SELECT m.center FROM MasterPlanSub m WHERE m.masterPlan = :masterPlan")
    List<String> findAllCentersByMasterPlan(MasterPlan masterPlan);

}
