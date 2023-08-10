package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.helper.CreateOrderRequest;
import com.lcwd.electronic.store.helper.PageableResponse;

import java.util.List;

public interface OrderService {

//    get order
    OrderDto createOrder(CreateOrderRequest orderDto);

//    remove order
    void removeOrder(Integer orderId);

//    get order by User
    List<OrderDto> getOrderOfUser(Integer userId);

//    grt all orders

    PageableResponse<OrderDto> getAllOrders(int pageNumber,int pageSize,String sortBy,String sortDir);


}
