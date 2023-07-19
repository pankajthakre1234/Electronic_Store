package com.lcwd.electronic.store.helper;


import com.lcwd.electronic.store.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddItemToCartRequest {

    private Integer productId;

    private int quantity;

}
