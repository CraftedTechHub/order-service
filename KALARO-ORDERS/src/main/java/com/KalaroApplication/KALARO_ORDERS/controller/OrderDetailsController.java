package com.KalaroApplication.KALARO_ORDERS.controller;

import com.KalaroApplication.KALARO_ORDERS.dto.OrderDetailsDto;
import com.KalaroApplication.KALARO_ORDERS.service.OrderDetailsService;
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
    public List<OrderDetailsDto> getAllOrderDetails(){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getAllOrderDetails();
        return orderDetailsDtoList;
    }

    @GetMapping(path="/getOrderDetailsByCategory/{category}") //FILTER BY CATEGORY
    public List<OrderDetailsDto> getOrderDetailsByCategory(@PathVariable(value = "category") String category){
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.getOrderDetailsByCategory(category);
        return orderDetailsDtoList;
    }

    @DeleteMapping(path = "/deleteOrderDetails/{orderId}")  //DELETE ORDERS
    public ResponseEntity<String> deleteOrderDetails(@PathVariable(value = "orderId") int orderId){
        String message = orderDetailsService.deleteOrderDetails(orderId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping(path = "/addNewOrderDetails")  //ADD NEW ORDER
    public ResponseEntity<OrderDetailsDto> addNewOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto) {
        OrderDetailsDto orderDetailsDto1 = orderDetailsService.saveOrderDetails(orderDetailsDto);
        return new ResponseEntity<>(orderDetailsDto1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getOrderDetails/{orderId}")    //VIEW ORDER
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }

    @PutMapping(path = "/updateOrderDetails")   //EDIT EXIST ORDER
    public ResponseEntity<OrderDetailsDto> updateOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.updateOrderDetails(orderDetailsDto);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterPlan/{orderId}")    //MASTER PLAN BUTTON
    public ResponseEntity<OrderDetailsDto> passOrderDetails(@PathVariable(value = "orderId") int orderId){
        OrderDetailsDto orderDetailsDto2 =orderDetailsService.passOrderDetails(orderId);
        return new ResponseEntity<>(orderDetailsDto2,HttpStatus.OK);
    }

}
