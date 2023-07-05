package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;
}
