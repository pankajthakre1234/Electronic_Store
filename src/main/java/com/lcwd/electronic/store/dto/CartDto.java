package com.lcwd.electronic.store.dto;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Integer cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> items= new ArrayList<>();
}
