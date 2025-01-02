package com.KalaroApplication.KALARO_ORDERS.repository.component;

import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MasterPlanSubRepository extends JpaRepository<MasterPlanSub,Integer> {
    List<MasterPlanSub> findAllByCenter(String centerName);
}
