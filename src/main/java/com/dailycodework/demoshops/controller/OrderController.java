package com.dailycodework.demoshops.controller;

import com.dailycodework.demoshops.dto.OrderDto;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Order;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.order.IOrderService;
import com.dailycodework.demoshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.accept.ApiVersionResolver;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {


    private final IOrderService orderService;

    private final IUserService userService;

    @PostMapping("/order")
// someone want to place order then the person must have userId
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            // Instead of returning the raw order. we are going to return the orderDto
            //Order order = orderService.placeOrder(userId);
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        }
    }


    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            // Instead of returning the raw order. we are going to return the orderDto
            OrderDto order = orderService.getOrder(orderId);

            return ResponseEntity.ok(new ApiResponse("your Order Item!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops! ", e.getMessage()));
        }
    }


    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            //User user = userService.getUserById(userId);
            List<OrderDto> order = orderService.getUserOrders(userId);

            return ResponseEntity.ok(new ApiResponse("Item order Success!", order));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    // we are going to have recycle dependencies like(it gives recursion) that so for that
    //  we are going to create a dto(data transfer object);

    // Instead of returning order directly we are going to return a dto
    // So remember a DTO gives us the opportunity or chance to actually select the attributes
    // that  we wish to return for the to the color;

    // Instead of returning the raw order. we are going to return the orderDto
    //Order order = orderService.placeOrder(userId);
  /*  OrderDto order = orderService.placeOrder(userId);
  so go to service class and then change the order to orderDto

   */


    // 6:23
}

