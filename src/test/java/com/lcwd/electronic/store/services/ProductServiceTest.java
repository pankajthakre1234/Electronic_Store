package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.helper.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
        ProductDto productDto = ProductDto.builder()
                .title("Iphone 14 Pro Max")
                .description("This Is Updated Apple Company Best Product Testing")
                .price(140000)
                .discountedPrice(138000)
                .productImageName("iphone.png")
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(product);
        ProductDto updatedProduct = this.productService.update(productDto, productId);
        System.out.println(updatedProduct.getTitle());
        Assertions.assertEquals(product.getTitle(),updatedProduct.getTitle());
    }

//    get single Product
    @Test
    public void getSingleProduct_Test()
    {
        Integer productId=1;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));

        ProductDto productDto = this.productService.getSingle(productId);
        System.out.println(productDto.getTitle());
        Assertions.assertNotNull(productDto);
    }

//    delete Product
    @Test
    public void deleteProduct_Test()
    {
        Integer productId=6;
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(product));

        this.productService.delete(productId);

        Mockito.verify(repository,Mockito.timeout(1)).delete(product);

    }

//    Get All Product
    @Test
    public void getAllProduct_Test()
    {
        Product product= Product.builder()
            .title("Iphone 14 Pro Max")
            .description("This Is Updated Apple Company Best Product Testing")
            .price(140000)
            .discountedPrice(138000)
            .productImageName("iphone.png")
            .quantity(50)
            .addedDate(new Date())
            .stock(true)
            .live(true)
            .build();

        Product product1= Product.builder()
                .title("Samsung S23 Ultra")
                .description("This Is Samsung Company Best Product Testing")
                .price(150000)
                .discountedPrice(148000)
                .productImageName("samsung.png")
                .quantity(70)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();

        Product product2= Product.builder()
                .title("One Plus 11R 5G")
                .description("This Is One Plus Company Best Product Testing")
                .price(100000)
                .discountedPrice(98000)
                .productImageName("one.png")
                .quantity(25)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();

        List<Product> allUsers= Arrays.asList(product,product1,product2);
        Page<Product> page= new PageImpl<>(allUsers);
        Mockito.when(this.repository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> dto = this.productService.getAll(2, 3, "title", "asc");
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3,dto.getContent().size());
    }


}
