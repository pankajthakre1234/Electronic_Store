package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.helper.AddItemToCartRequest;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    Logger logger = LoggerFactory.getLogger(CartController.class);

    /**
     * @Auther Pankaj
     * @apiNote This Api is use For The Added Item Details to cart
     * @param userId
     * @param request
     * @return
     */
//    create
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addedItemToCart(@PathVariable Integer userId, @RequestBody AddItemToCartRequest request)
    {
        logger.info("Initiate the request for Save the Item Details to cart with id :{}",userId);

        CartDto cartDto = this.cartService.addedItemToCart(userId, request);

        logger.info("Completed the request for Save the Item Details to cart with id :{}",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    /**
     * @apiNote This Api is use For The remove Item details From cart
     * @param userId
     * @param cartItemId
     * @return
     */
//    remove items
    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Integer userId,
            @PathVariable Integer cartItemId
    )
    {
        logger.info("Initiate the request for Remove Item Details from Cart with user id :{} and cartItem id :{}",userId,cartItemId);
        this.cartService.removeItemFromCart(userId,cartItemId);
        logger.info("Completed the request for Remove Item Details from Cart with user id :{} and cartItem id :{}",userId,cartItemId);
        return new ResponseEntity<>(new ApiResponse(AppConstant.CART_DELETED,true),HttpStatus.OK);
    }

    /**
     * @apiNote This Api is use For the clear cart details
     * @param userId
     * @return
     */
//    clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Integer userId)
    {
        logger.info("Initiate the request for clear cart Details with user id :{}",userId);

        this.cartService.clearCart(userId);
        logger.info("Completed the request for clear cart Details with user id :{}",userId);

        return new ResponseEntity<>(new ApiResponse(AppConstant.CART_DELETED,true),HttpStatus.OK);
    }


    /**
     * @apiNote This Api is use For The get Cart Details By User
     * @param userId
     * @return
     */
//    get cart by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable Integer userId)
    {
        logger.info("Initiate the request for get cart Details with user id :{}",userId);

        CartDto cartByUser = this.cartService.getCartByUser(userId);

        logger.info("Completed the request for get cart Details with user id :{}",userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

}
