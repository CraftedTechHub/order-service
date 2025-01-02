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
        if(orderDetailsDtoList.isEmpty()){
            message = "No orders available at this time";
        }else {
            message = "Orders details fetched successfully";
        }
        return new ResponseEntity<>(new HttpResponse(200,message,orderDetailsDtoList),HttpStatus.OK);
    }

    @GetMapping(path="/getOrderDetailsByCategory/{category}") //FILTER BY CATEGORY
    public ResponseEntity<HttpResponse> getOrderDetailsByCategory(@PathVariable(value = "category") String category){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getOrderDetailsByCategory(category);
        String message = "Order details fetched by category successfully";
        return new ResponseEntity<>(new HttpResponse(200,message,orderDetailsDtoList),HttpStatus.OK);

    }

    @DeleteMapping(path = "/deleteOrderDetails/{orderId}")  //DELETE ORDERS
    public ResponseEntity<StandardResponse> deleteOrderDetails(@PathVariable(value = "orderId") int orderId){
        String message = orderDetailsService.deleteOrderDetails(orderId);
        return new ResponseEntity<>(new StandardResponse(200,message),HttpStatus.OK);
    }

    @PostMapping(path = "/addNewOrderDetails")  //ADD NEW ORDER
    public ResponseEntity<StandardResponse> addNewOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto) {
        String message = orderDetailsService.saveOrderDetails(orderDetailsDto);
        return new ResponseEntity<>(new StandardResponse(201,message),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")    //VIEW ORDER
    public ResponseEntity<HttpResponse> getOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetails(orderId);
        String message;
        if(orderDetailsDto2==null){
            message = "No order details found";
        }else{
            message = "Orders details fetched successfully";
        }
        return new ResponseEntity<>(new HttpResponse(200,message,orderDetailsDto2),HttpStatus.OK);
    }

    @PutMapping(path = "/updateOrderDetails")   //EDIT EXIST ORDER
    public ResponseEntity<StandardResponse> updateOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto){
        String message =orderDetailsService.updateOrderDetails(orderDetailsDto);
        return new ResponseEntity<>(new StandardResponse(200,message),HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlan/{orderId}")    //MASTER PLAN BUTTON
    public ResponseEntity<HttpResponse> passOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto =orderDetailsService.passOrderDetails(orderId);
        String message;
        if(orderDetailsDto==null){
            message = "Orders details fetched unsuccessfully";
        }else{
            message = "Orders details fetched successfully";
        }
        return new ResponseEntity<>(new HttpResponse(200,message,orderDetailsDto),HttpStatus.OK);
    }
}
