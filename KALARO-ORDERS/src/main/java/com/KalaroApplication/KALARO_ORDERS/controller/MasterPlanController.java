package com.KalaroApplication.KALARO_ORDERS.controller;


import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/masterPlan")
public class MasterPlanController {

    @Autowired
    private MasterPlanService masterPlanService;

    @PostMapping(path = "/addNewOrderDetails")  //ADD NEW ORDER
    public ResponseEntity<String> addNewOrderDetails(@RequestBody MasterPlanDto masterPlanDto) {
        String message = masterPlanService.saveOrderDetails(masterPlanDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")  //GET ORDER DETAILS
    public List<MasterPlanDto> getOrderDetails(@PathVariable(value = "orderId") int orderId) {
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getOrderDetails(orderId);
        return masterPlanDtoList;
    }

    @PutMapping(path = "/updateOrderDetails")  //UPDATE ORDER DETAILS
    public ResponseEntity<String> updateOrderDetails(@RequestBody MasterPlanDto masterPlanDto) {
        String message = masterPlanService.updateOrderDetails(masterPlanDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteOrderDetails/{planId}")  //DELETE ORDER DETAILS
    public ResponseEntity<String> deleteOrderDetails(@PathVariable(value = "planId") int planId) {
        String message = masterPlanService.deleteOrderDetails(planId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlanByCenter/{centerName}")  //GET MASTER PLAN BY CENTER
    public List<MasterPlanDto> getMasterPlanSubByCenter(@PathVariable String centerName){
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getMasterPlanSubByCenter(centerName);
        return masterPlanDtoList;
    }

    @GetMapping(path = "/getMasterPlanByStatus/{centerName}")  //GET MASTER PLAN BY CENTER FILTERED BY STATUS
    public ResponseEntity<List<MasterPlanDto>> getMasterPlanSubByCenterForFilter(@PathVariable String centerName){
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getMasterPlanSubByCenter(centerName);
        return new ResponseEntity<>(masterPlanDtoList,HttpStatus.OK);
    }
}
