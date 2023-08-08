package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.OrderItem;
import com.lcwd.electronic.store.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Integer orderId;

    private String orderStatus = "PENDING";

    private String paymentStatus= "NOT-PAID";

    private int orderAmount;

    @NotEmpty
    @Column(length = 10000000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date deliveredDate;

    private UserDto user;

    private List<OrderItem> orderItems= new ArrayList<>();
}
