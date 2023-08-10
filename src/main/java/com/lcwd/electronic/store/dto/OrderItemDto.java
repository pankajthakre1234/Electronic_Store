package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.Order;
import com.lcwd.electronic.store.entity.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItemDto {

    private int OrderItemId;

    private int quantity;

    private int totalPrice;

    private Product product;

    private OrderDto orderDto;
}
