package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.entity.Order;
import com.lcwd.electronic.store.helper.PageableResponse;

import java.util.List;

public interface OrderService {

//    get order
    Order createOrder(OrderDto orderDto,Integer userId);

//    remove order
    void removeOrder(Integer userId);

//    get order by User
    List<OrderDto> getOrderOfUser(Integer userId);

//    grt all orders

    PageableResponse<OrderDto> getAllOrders(int pageNumber,int pageSize,String sortBy,String sortDir);


}
