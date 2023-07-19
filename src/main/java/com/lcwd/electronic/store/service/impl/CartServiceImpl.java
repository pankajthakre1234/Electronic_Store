package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.entity.Cart;
import com.lcwd.electronic.store.entity.CartItem;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.AddItemToCartRequest;
import com.lcwd.electronic.store.repository.CartRepository;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addedItemToCart(Integer userId, AddItemToCartRequest request)
    {
        Integer productId = request.getProductId();
        int quantity = request.getQuantity();
        //  fetch product
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

//        fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

       Cart cart= null;

        try{
             cart = cartRepository.findByUser(user).get();

        }catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCreatedAt(new Date());
        }
        List<CartItem> itemList = cart.getItems();

        CartItem item = CartItem.builder()
                .quantity(quantity)
                .totalPrice(quantity * product.getPrice())
                .cart(cart)
                .product(product)
                .build();
        cart.getItems().add(item);

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);

        return this.mapper.map(cart,CartDto.class);

    }

    @Override
    public void removeItemFromCart(Integer userId, Integer cartItemId) {

    }

    @Override
    public void clearCart(Integer userId) {

    }
}
