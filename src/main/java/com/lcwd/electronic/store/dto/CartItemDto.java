package com.lcwd.electronic.store.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {

    private Integer cartItemId;

    private ProductDto productDto;

    private int quantity;

    private int totalPrice;

}
