package com.KalaroApplication.KALARO_ORDERS.service.component.Impl;

import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import com.KalaroApplication.KALARO_ORDERS.repository.component.MasterPlanSubRepository;
import com.KalaroApplication.KALARO_ORDERS.service.component.MasterPlanSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MasterPlanSubServiceImpl implements MasterPlanSubService {

    @Autowired
    private MasterPlanSubRepository masterPlanSubRepository;

    @Override
    public List<Integer> getMasterPlanSubByCenter(String centerName) {
        List<MasterPlanSub> masterPlanSubList = (List<MasterPlanSub>) masterPlanSubRepository.findAllByCenter(centerName);
        List<Integer> masterPlanSubDtoList = new ArrayList<>();
        for(MasterPlanSub masterPlanSub:masterPlanSubList){
            if(masterPlanSub.getCenter()==null){
                throw new RuntimeException("No data found for center: " + centerName);
            }else{
                masterPlanSubDtoList.add(masterPlanSub.getMasterPlan().getPlanId());
            }
        }
        log.info("Master Plan Sub fetched successfully");
        return masterPlanSubDtoList;
    }
}
