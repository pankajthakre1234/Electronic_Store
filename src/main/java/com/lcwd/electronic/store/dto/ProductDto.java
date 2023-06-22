package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private Integer productId;

    private String title;

    private String description;

    private Date addedDate;

    private Integer quantity;

    private double price;

    private double discountedPrice;

    private boolean live;

    private boolean stock;

    @ImageNameValid
    private String productImageName;

    private CategoryDto category;
}
