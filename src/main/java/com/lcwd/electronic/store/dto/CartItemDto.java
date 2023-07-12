package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.Cart;
import com.lcwd.electronic.store.entity.Product;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {

    private Integer cartItemId;

    private Product product;

    private int quantity;

    private int totalPrice;

    private Cart cart;
}
