package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.CreateOrderRequest;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.OrderService;
import com.lcwd.electronic.store.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrders(CreateOrderRequest orderRequest)
    {
        OrderDto orderDto = this.orderService.createOrder(orderRequest);

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/orderId")
    public ResponseEntity<ApiResponse> removeOrders(@PathVariable Integer orderId)
    {
        this.orderService.removeOrder(orderId);

        return new ResponseEntity<>(new ApiResponse(AppConstant.ORDER_DELETE,true),HttpStatus.OK);
    }

    @GetMapping("/userId")
    public ResponseEntity<List<OrderDto>> getOrderOfUsers(@PathVariable Integer userId)
    {
        List<OrderDto> orderOfUser = this.orderService.getOrderOfUser(userId);

        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)
    {
        PageableResponse<OrderDto> allOrders = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allOrders,HttpStatus.OK);
    }
}
