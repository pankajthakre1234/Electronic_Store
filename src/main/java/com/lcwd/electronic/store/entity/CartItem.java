package com.lcwd.electronic.store.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Data
@Builder
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @OneToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    @Column(name = "cartItem_quantity")
    private int quantity;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToOne
    private  Cart cart;
}
