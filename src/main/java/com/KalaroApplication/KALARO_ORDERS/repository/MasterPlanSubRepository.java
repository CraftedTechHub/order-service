package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterPlanSubRepository extends JpaRepository<MasterPlanSub,Integer> {

    List<MasterPlanSub> findAllByCenter(String center);
}
