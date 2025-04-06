package com.KalaroApplication.KALARO_ORDERS.controller;

import com.KalaroApplication.KALARO_ORDERS.dto.DOrdersDto;
import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.EmpOrderDto;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import com.KalaroApplication.KALARO_ORDERS.utility.HttpResponse;
import com.KalaroApplication.KALARO_ORDERS.utility.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private String message;
    private int statusCode;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private MasterPlanService masterPlanService;

    @GetMapping(path="/getAllOrders") //GET ALL ORDERS //USED
    public ResponseEntity<HttpResponse> getAllOrderDetails(){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getAllOrderDetails();
        if(orderDetailsDtoList.isEmpty()){
            message = "No orders available at this time";
            statusCode=404;
        }else {
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDtoList),HttpStatus.OK);
    }

    @GetMapping(path="/getOrderByCategory/{category}") //FILTER BY CATEGORY //USED
    public ResponseEntity<HttpResponse> getOrderDetailsByCategory(@PathVariable(value = "category") String category){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getOrderDetailsByCategory(category);
        if(orderDetailsDtoList.isEmpty()){
            message = "No orders available at this time";
            statusCode = 404;
        }else{
            message = "Order details fetched by category successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDtoList),HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteOrder/{orderId}")  //DELETE ORDERS //USED
    public ResponseEntity<StandardResponse> deleteOrderDetails(@PathVariable(value = "orderId") int orderId){
        int num = orderDetailsService.deleteOrderDetails(orderId);
        if(num == 0){
            message = "Order details deletion is unsuccessful";
            statusCode = 404;
        }else{
            message = "Order details deleted successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.OK);
    }

    @PostMapping(value = "/addOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //ADD NEW ORDER //USED
    public ResponseEntity<StandardResponse> addOrderDetails(
            @RequestPart("orderDetails") String orderDetails,
            @RequestPart(value = "modelImage", required = false) MultipartFile modelImage) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDetailsDto orderDetailsDto = objectMapper.readValue(orderDetails, OrderDetailsDto.class);

            int num = orderDetailsService.saveOrderDetails(orderDetailsDto, modelImage);

            String message;
            int statusCode;
            if (num != 1) {
                message = "Model number already exists, changes unsaved";
                statusCode = 404;
            } else {
                message = "Order details saved successfully";
                statusCode = 201;
            }

            return new ResponseEntity<>(new StandardResponse(statusCode, message), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new StandardResponse(400, "Failed to process order data"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/getOrderDetails/{orderId}")    //VIEW ORDER //USED
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }


    @GetMapping(path = "/getOrderDetails")    //VIEW ORDER //USED
    public ResponseEntity<OrderDetailsDto> getOrderDetails(){
        List<OrderDetailsDto> orderDetailsDto2 =orderDetailsService.getOrderDetails();
        return new ResponseEntity(orderDetailsDto2,HttpStatus.OK);
    }

    @PutMapping(value = "/updateOrderDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // EDIT EXISTING ORDER // USED
    public ResponseEntity<StandardResponse> updateOrderDetails(
            @RequestPart("orderDetails") String orderDetails,
            @RequestPart(value = "modelImage", required = false) MultipartFile modelImage) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDetailsDto orderDetailsDto = objectMapper.readValue(orderDetails, OrderDetailsDto.class);

            int num = orderDetailsService.updateOrderDetails(orderDetailsDto, modelImage);

            String message;
            int statusCode;
            if (num != 1) {
                message = "Model number already exists, changes unsaved";
                statusCode = 404;
            } else {
                message = "Order details updated successfully";
                statusCode = 200;
            }

            return new ResponseEntity<>(new StandardResponse(statusCode, message), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new StandardResponse(400, "Failed to process order data"), HttpStatus.BAD_REQUEST);
        }
    }


    //BELOW MASTER PLAN
    @PostMapping(path = "/addMasterPlan")  //ADD NEW ORDER //USED
    public ResponseEntity<StandardResponse> addOrderForMasterPlan(@RequestBody MasterPlanDto masterPlanDto) {
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

    @GetMapping(path = "/getOrderForMasterPlan/{orderId}")  //GET ORDER DETAILS //getOrderDetails/{orderId} TO /getOrderDetailsForMasterPlan/{orderId} //USED
    public ResponseEntity<HttpResponse> getOrderDetailsForMaster(@PathVariable(value = "orderId") int orderId) {
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

    @PutMapping(path = "/updateMasterPlan")  //UPDATE ORDER DETAILS //USED
    public ResponseEntity<StandardResponse> updateOrderInMaster(@RequestBody MasterPlanDto masterPlanDto) {
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

    @DeleteMapping(path = "/deleteMasterPlan/{planId}")  //DELETE ORDER DETAILS //deleteOrderDetails/{orderId} TO /deleteOrderDetailsForMasterPlan/{orderId} //USED
    public ResponseEntity<StandardResponse> deleteOrderFromMasterPlan(@PathVariable(value = "planId") int planId) {
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

    @GetMapping(path = "/getMasterPlanByPlanId/{planId}")  //GET MASTER PLAN //USED
    public ResponseEntity<HttpResponse> getMasterPlan(@PathVariable(value = "planId") int planId){
        MasterPlanDto masterPlanDto = masterPlanService.getMasterPlan(planId);
        if(masterPlanDto == null){
            message = "Error occurred while fetching order details of Master Plan";
            statusCode = 404;
        }else{
            message = "Master Plan fetched successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,masterPlanDto),HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlanByCenter/{centerName}")  //GET MASTER PLAN BY CENTER //USED IN CENTER
    public ResponseEntity<List<MasterPlanDto>> getMasterPlanSubByCenter(@PathVariable String centerName){
        List<MasterPlanDto> masterPlanDtoList = masterPlanService.getMasterPlanSubByCenter(centerName);
        return new ResponseEntity<>(masterPlanDtoList,HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlanDetailsForCenter/{planId}/{centerName}") //USED
    public ResponseEntity<MasterPlanDto> getMasterPlanDetailsForCenter(@PathVariable int planId, @PathVariable String centerName){
        MasterPlanDto masterPlanDto = masterPlanService.getMasterPlanDetailsForCenter(planId, centerName);
        return new ResponseEntity<>(masterPlanDto,HttpStatus.OK);
    }

//    @GetMapping(path="/getMasterPlanByPlanId/{planId}")
//    public ResponseEntity<MasterPlanDto> getMasterPlanDetailsByPlanId(@PathVariable int planId){
//        MasterPlanDto masterPlanDto = masterPlanService.getMasterPlanByPlanId(planId);
//        return new ResponseEntity<>(masterPlanDto,HttpStatus.OK);
//    }

    @GetMapping(path = "/getOrderDetailsForEmp/{modelName}") //USED IN EMPLOYEE SERVICE
    public ResponseEntity<List<EmpOrderDto>> getEmpOrders(@PathVariable String modelName){
        List<EmpOrderDto> empOrderDtoList = orderDetailsService.getEmpOrders(modelName);
        return new ResponseEntity<>(empOrderDtoList,HttpStatus.OK);
    }

    @GetMapping(path = "/getOrdersForDashboard") //USED IN DASHBOARD
    public ResponseEntity<List<DOrdersDto>> getOrdersForDashboard(){
        List<DOrdersDto> dOrdersDtoList = orderDetailsService.getOrdersForDashboard();
        return new ResponseEntity<>(dOrdersDtoList,HttpStatus.OK);
    }
    @GetMapping(path = "/getOrdersQtyForEachCenter/{centerName}")
    public ResponseEntity<Integer> getOrdersQtyForEachCenter(@PathVariable String centerName){
        int qty = orderDetailsService.getOrdersQtyForEachCenter(centerName);
        return new ResponseEntity<>(qty,HttpStatus.OK);
    }
}
