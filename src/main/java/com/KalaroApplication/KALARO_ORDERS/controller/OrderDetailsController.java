package com.KalaroApplication.KALARO_ORDERS.controller;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
import com.KalaroApplication.KALARO_ORDERS.utility.HttpResponse;
import com.KalaroApplication.KALARO_ORDERS.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping(path="/getAllOrderDetails") //GET ALL ORDERS
    public ResponseEntity<HttpResponse> getAllOrderDetails(){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getAllOrderDetails();
        String message;
        int statusCode;
        if(orderDetailsDtoList.isEmpty()){
            message = "No orders available at this time";
            statusCode=404;
        }else {
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDtoList),HttpStatus.OK);
    }

    @GetMapping(path="/getOrderDetailsByCategory/{category}") //FILTER BY CATEGORY
    public ResponseEntity<HttpResponse> getOrderDetailsByCategory(@PathVariable(value = "category") String category){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getOrderDetailsByCategory(category);
        String message;
        int statusCode;
        if(orderDetailsDtoList.isEmpty()){
            message = "No orders available at this time";
            statusCode = 404;
        }else{
            message = "Order details fetched by category successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDtoList),HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteOrderDetails/{orderId}")  //DELETE ORDERS
    public ResponseEntity<StandardResponse> deleteOrderDetails(@PathVariable(value = "orderId") int orderId){
        int num = orderDetailsService.deleteOrderDetails(orderId);
        String message;
        int statusCode;
        if(num == 0){
            message = "Order details deletion is unsuccessful";
            statusCode = 404;
        }else{
            message = "Order details deleted successfully";
            statusCode = 200;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.OK);
    }

    @PostMapping(path = "/addNewOrderDetails")  //ADD NEW ORDER
    public ResponseEntity<StandardResponse> addNewOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto) {
        int num = orderDetailsService.saveOrderDetails(orderDetailsDto);
        String message;
        int statusCode;
        if(num != 1){
            message = "Model number is already exist, Changes unsaved";
            statusCode = 404;
        }else{
            message = "Order details saved successfully";
            statusCode = 201;
        }
        return new ResponseEntity<>(new StandardResponse(statusCode,message),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")    //VIEW ORDER
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }

    @PutMapping(path = "/updateOrderDetails")   //EDIT EXIST ORDER
    public ResponseEntity<StandardResponse> updateOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto){
        int num =orderDetailsService.updateOrderDetails(orderDetailsDto);
        String message;
        int statusCode;
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
        String message;
        int statusCode;
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
        String message;
        int statusCode;
        if(orderDetailsDto2==null){
            message = "model number not found";
            statusCode=404;
        }else{
            message = "Orders details fetched successfully";
            statusCode=200;
        }
        return new ResponseEntity<>(new HttpResponse(statusCode,message,orderDetailsDto2),HttpStatus.OK);
    }
}
