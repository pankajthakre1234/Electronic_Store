package com.lcwd.electronic.store.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Product_Store")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_description")
    private String description;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "product_price")
    private double price;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "discounted_price")
    private double discountedPrice;

    @Column(name = "product_live")
    private boolean live;

    @Column(name = "available_stock")
    private boolean stock;

    @Column(name = "product_image")
    private String productImage;
}
