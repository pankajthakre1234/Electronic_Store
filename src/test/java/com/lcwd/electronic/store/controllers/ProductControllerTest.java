package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest
{
    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private Product product;

    public void init()
    {
        product=Product.builder()
                .title("")
                .build();
    }

























}
