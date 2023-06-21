package com.lcwd.electronic.store.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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

    private String title;

    private String description;

    private Date addedDate;

    private Integer quantity;

    private double price;

    private double discountedPrice;

    private boolean live;

    private boolean stock;

    private String productImage;
}
