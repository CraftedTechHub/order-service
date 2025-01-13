package com.KalaroApplication.KALARO_ORDERS.controller;


import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import com.KalaroApplication.KALARO_ORDERS.utility.HttpResponse;
import com.KalaroApplication.KALARO_ORDERS.utility.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/masterPlan")
public class MasterPlanController {

    @Autowired
    private MasterPlanService masterPlanService;

    private String message;
    private int statusCode;

    @PostMapping(path = "/addNewOrderDetails")  //ADD NEW ORDER
    public ResponseEntity<StandardResponse> addNewOrderDetails(@RequestBody MasterPlanDto masterPlanDto) {
        int num = masterPlanService.saveOrderDetails(masterPlanDto);
        if(num == 0){
            message = "Error occurred while saving order details of Master Plan";
            statusCode = 404;
        }else{
            message = "Order details saved successfully";
            statusCode = 201;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")  //GET ORDER DETAILS
    public ResponseEntity<HttpResponse> getOrderDetails(@PathVariable(value = "orderId") int orderId) {
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getOrderDetails(orderId);
        if(masterPlanDtoList.isEmpty()){
            message = "Error occurred while fetching order details of Master Plan";
            statusCode = 404;
        }else {
            message = "Order details fetched successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,masterPlanDtoList),HttpStatus.OK);
    }

    @PutMapping(path = "/updateOrderDetails")  //UPDATE ORDER DETAILS
    public ResponseEntity<StandardResponse> updateOrderDetails(@RequestBody MasterPlanDto masterPlanDto) {
        int num = masterPlanService.updateOrderDetails(masterPlanDto);
        if(num == 0){
            message = "Order details updated successfully, including sub-plans.";
            statusCode = 404;
        }else {
            message = "Error occurred while updating order details of Master Plan";
            statusCode = 200;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteOrderDetails/{planId}")  //DELETE ORDER DETAILS
    public ResponseEntity<StandardResponse> deleteOrderDetails(@PathVariable(value = "planId") int planId) {
        int num = masterPlanService.deleteOrderDetails(planId);
        if(num == 0){
            message = "Error occurred while deleting order details of Master Plan";
            statusCode = 404;
        }else{
            message = "Order details deleted successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlanByCenter/{centerName}")  //GET MASTER PLAN BY CENTER
    public ResponseEntity<List<MasterPlanDto>> getMasterPlanSubByCenter(@PathVariable String centerName){
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getMasterPlanSubByCenter(centerName);
        return new ResponseEntity<>(masterPlanDtoList,HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlanByStatus/{centerName}")  //GET MASTER PLAN BY CENTER FILTERED BY STATUS
    public ResponseEntity<HttpResponse> getMasterPlanSubByCenterForFilter(@PathVariable String centerName){
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getMasterPlanSubByCenter(centerName);
        if(masterPlanDtoList.isEmpty()){
            message = "Error occurred while fetching order sub plan details of Master Plan";
            statusCode = 404;
        }else{
            message = "Master Plan Sub fetched successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,masterPlanDtoList),HttpStatus.OK);
    }
}
