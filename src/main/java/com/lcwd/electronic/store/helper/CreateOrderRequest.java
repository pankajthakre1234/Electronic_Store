package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.OrderItem;
import lombok.*;

import javax.persistence.Column;
import javax.validation.Valid;
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

    @NotEmpty(message = "UserId Must Be required And  Not be empty")
    private Integer userId;

    @NotEmpty(message = "cartId Must Be required And  Not be empty")
    private Integer cartId;

    private Integer orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus= "NOT-PAID";
    private int orderAmount;

    @NotEmpty(message = "Billing Address Must be Required and Proper Address")
    private String billingAddress;

    @NotEmpty(message = "Billing Phone Number Must Be Required and number is On")
    private String billingPhone;

    @NotEmpty(message = "Billing Name Must Be required And  Not be empty")
    private String billingName;

}
