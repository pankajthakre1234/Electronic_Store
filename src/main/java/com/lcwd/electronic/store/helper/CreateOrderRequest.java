package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.OrderItem;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateOrderRequest {

    private Integer userId;
    private Integer cartId;
    private Integer orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus= "NOT-PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;

}
