package com.lcwd.electronic.store.entity;

import com.lcwd.electronic.store.dto.CartItemDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private Integer cartId;

    @Column(name = "cart_added_date")
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items= new ArrayList<>();
}
