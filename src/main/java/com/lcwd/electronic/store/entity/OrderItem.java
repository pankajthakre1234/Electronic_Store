package com.lcwd.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {


    private int OrderItemId;

    private int quantity;

    private int totalPrice;

    @OneToOne
    private Product product;
}
