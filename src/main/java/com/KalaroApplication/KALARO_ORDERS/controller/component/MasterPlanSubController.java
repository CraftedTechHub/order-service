package com.KalaroApplication.KALARO_ORDERS.controller.component;

import com.KalaroApplication.KALARO_ORDERS.service.component.MasterPlanSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/masterPlanSub")
public class MasterPlanSubController {

    @Autowired
    private MasterPlanSubService masterPlanSubService;

    @GetMapping(path = "/getMasterPlanSubByCenter/{centerName}")  //GET MASTER PLAN ID BY CENTER (This is not used yet)
    public List<Integer> getMasterPlanSubByCenter(@PathVariable String centerName){
        List<Integer> masterPlanSubDtoList = masterPlanSubService.getMasterPlanSubByCenter(centerName);
        return masterPlanSubDtoList;
    }
}
