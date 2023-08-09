package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.entity.Order;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(OrderDto orderDto, Integer userId) {
        return null;
    }

    @Override
    public void removeOrder(Integer userId) {

    }

    @Override
    public List<OrderDto> getOrderOfUser(Integer userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
