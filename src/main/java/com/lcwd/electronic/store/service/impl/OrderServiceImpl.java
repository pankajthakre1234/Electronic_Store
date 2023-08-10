package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.entity.*;
import com.lcwd.electronic.store.exception.BadApiRequestException;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.CreateOrderRequest;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.CartRepository;
import com.lcwd.electronic.store.repository.OrderRepository;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.OrderService;
import com.lcwd.electronic.store.utility.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto)
    {
        Integer userId= orderDto.getUserId();
        Integer cartId= orderDto.getCartId();

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Cart cart = this.cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        List<CartItem> cartItems = cart.getItems();

        if(cartItems.size() <=0)
        {
            throw  new BadApiRequestException("Invalid Number of Items in cart..!!",false);
        }


        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderDate(new Date())
                .deliveredDate(null)
                .orderAmount(orderDto.getOrderAmount())
                .orderStatus(orderDto.getOrderStatus())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderId(orderDto.getOrderId())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount= new AtomicReference<>(0);
        List<OrderItem> orderItemList = cartItems.stream().map(cartItem -> {

            OrderItem orderItems = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice((int) (cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice()))
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItems.getTotalPrice());
            return orderItems;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItemList);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();

        Cart save = this.cartRepository.save(cart);
        Order savedOrder = this.orderRepository.save(order);
        OrderDto orderDto1 = this.mapper.map(savedOrder, OrderDto.class);

        return orderDto1;
    }

    @Override
    public void removeOrder(Integer orderId)
    {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        this.orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrderOfUser(Integer userId)
    {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Order> orders = this.orderRepository.findByUser(user);

        List<OrderDto> orderDtos = orders.stream().map(order ->
                mapper.map(orders, OrderDto.class)
        ).collect(Collectors.toList());

        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("dsc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pageable = PageRequest.of(pageSize, pageNumber, sort);

        Page<Order> page = this.orderRepository.findAll(pageable);

        List<Order> allOrders = page.getContent();

        PageableResponse<OrderDto> pageableResponse = Helper.getPageableResponse(page, OrderDto.class);

        return pageableResponse;
    }
}
