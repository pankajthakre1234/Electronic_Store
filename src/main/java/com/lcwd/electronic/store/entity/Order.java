package com.lcwd.electronic.store.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Integer orderId;

    private String orderStatus;

    private String paymentStatus;

    private int orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE )
    private List<OrderItem> orderItems= new ArrayList<>();

}
