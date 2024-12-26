package com.KalaroApplication.KALARO_ORDERS.controller;


import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/masterPlan")
public class MasterPlanController {

    @Autowired
    private MasterPlanService masterPlanService;

}
