package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.helper.AddItemToCartRequest;
import com.lcwd.electronic.store.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartDto addedItemToCart(Integer userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(Integer userId, Integer cartItemId) {

    }

    @Override
    public void clearCart(Integer userId) {

    }
}
