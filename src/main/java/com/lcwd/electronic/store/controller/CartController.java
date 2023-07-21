package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.helper.AddItemToCartRequest;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

//    create

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addedItemToCart(@PathVariable Integer userId, @RequestBody AddItemToCartRequest request)
    {
        CartDto cartDto = this.cartService.addedItemToCart(userId, request);

        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

//    remove items
    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Integer userId,
            @PathVariable Integer cartItemId
    )
    {
        this.cartService.removeItemFromCart(userId,cartItemId);
        return new ResponseEntity<>(new ApiResponse(AppConstant.CART_DELETED,true),HttpStatus.OK);
    }

//    clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Integer userId)
    {
        this.cartService.clearCart(userId);
        return new ResponseEntity<>(new ApiResponse(AppConstant.CART_DELETED,true),HttpStatus.OK);
    }

//    get cart by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable Integer userId)
    {
        CartDto cartByUser = this.cartService.getCartByUser(userId);

        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

}
