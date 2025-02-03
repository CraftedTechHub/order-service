package com.KalaroApplication.KALARO_ORDERS.controller;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import com.KalaroApplication.KALARO_ORDERS.utility.HttpResponse;
import com.KalaroApplication.KALARO_ORDERS.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderDetailsController {

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

    @PostMapping(path = "/addOrder")  //ADD NEW ORDER //USED
    public ResponseEntity<StandardResponse> addOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto) {
        int num = orderDetailsService.saveOrderDetails(orderDetailsDto);
        if(num != 1){
            message = "Model number is already exist, Changes unsaved";
            statusCode = 404;
        }else{
            message = "Order details saved successfully";
            statusCode = 201;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")    //VIEW ORDER //USED
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }

    @PutMapping(path = "/updateOrderDetails")   //EDIT EXIST ORDER //USED
    public ResponseEntity<StandardResponse> updateOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto){
        int num =orderDetailsService.updateOrderDetails(orderDetailsDto);
        if(num != 1){
            message = "Model number is already exist, Changes unsaved";
            statusCode=404;
        }else{
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlan/{orderId}")    //MASTER PLAN BUTTON
    public ResponseEntity<HttpResponse> passOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto =orderDetailsService.passOrderDetails(orderId);
        if(orderDetailsDto==null){
            message = "Orders details fetched unsuccessfully";
            statusCode=404;
        }else{
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDto),HttpStatus.OK);
    }

    @GetMapping(path = "/getOrderDetailsByModelNo/{modelNo}")    //VIEW ORDER BY MODEL NUMBER
    public ResponseEntity<HttpResponse> getOrderDetails(@PathVariable(value = "modelNo") String modelNo){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetailsFromModelNo(modelNo);
        if(orderDetailsDto2==null){
            message = "model number not found";
            statusCode=404;
        }else{
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDto2),HttpStatus.OK);
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
