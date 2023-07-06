package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private Product product;

@BeforeEach
    public void init()
    {
        product=Product.builder()
                .title("Iphone 13")
                .description("This Is a Apple Company Best Product")
                .price(62000)
                .discountedPrice(59000)
                .productImageName("iphone.png")
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();
    }

//    create Product
    @Test
    public void createProduct_Test()
    {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(product);

        ProductDto dto = this.productService.create(mapper.map(product, ProductDto.class));
        System.out.println(dto.getTitle());
        Assertions.assertNotNull(dto);
    }

//    update Product
    @Test
    public void updateProduct_Test()
    {
        Integer productId=1;

        ProductDto productDto = this.mapper.map(product, ProductDto.class);

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));

        Mockito.when(this.repository.save(Mockito.any())).thenReturn(product);

        ProductDto updatedProduct = this.productService.update(productDto, productId);

        Assertions.assertEquals(product.getTitle(),updatedProduct.getTitle());
    }

//    get single Product

    public void getSingleProduct_Test()
    {

    }
}
