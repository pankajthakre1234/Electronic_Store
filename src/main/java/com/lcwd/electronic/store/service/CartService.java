package com.lcwd.electronic.store.service;


import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.helper.AddItemToCartRequest;

public interface CartService {

//    added item to cart
    CartDto addedItemToCart(Integer userId, AddItemToCartRequest request);

//    remove item from cart
    void removeItemFromCart(Integer userId, Integer cartItemId);

//    clear cart
    void clearCart(Integer userId);

//    get cart by user
    CartDto getCartByUser(Integer userId);
}
