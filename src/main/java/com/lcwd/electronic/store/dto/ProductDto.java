package com.lcwd.electronic.store.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private Integer productId;

    @NotEmpty(message = "Title should be Required")
    @Size(min = 2,max = 100 ,message = "Title must be minimum 2 character")
    private String title;

    @NotEmpty(message = "Description is Required")
    @Size(max = 10000,message = "Description must not be Empty")
    private String description;

    private Date addedDate;

    @NotEmpty(message = "Available Product Quantity")
    @Size(max = 100,message = "Quantity must not be Empty")
    private Integer quantity;

    @NotEmpty(message = "Price Not be Empty")
    private double price;

    @NotEmpty(message = "Product Discounted Price is Required")
    private double discountedPrice;


    private boolean live;

    private boolean stock;
}
