package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

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

    @NotEmpty(message = "title must Not be Empty")
    @Size(min = 4,max = 100,message = "Title minimum 2 character")
    private String title;

    @NotEmpty(message = "Must Not Be Empty")
    @Size(max = 200,message = "Description is Required..!!")
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
