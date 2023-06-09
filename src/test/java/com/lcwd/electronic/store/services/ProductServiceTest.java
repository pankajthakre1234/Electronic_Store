package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.CategoryRepository;
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

import static java.util.Optional.of;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository repository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private Product product;

    private Category category;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .title("Iphone 13")
                .description("This Is a Apple Company Best Product")
                .price(62000)
                .discountedPrice(59000)
                .productImageName("iphone.png")
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .category(category)
                .build();

        Integer catId=123;

        category=Category.builder()
                .catId(123)
                .build();
    }

    //    create Product
    @Test
    public void createProduct_Test() {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(product);

        ProductDto dto = this.productService.create(mapper.map(product, ProductDto.class));
        System.out.println(dto.getTitle());
        Assertions.assertNotNull(dto);
    }

    //    update Product
    @Test
    public void updateProduct_Test() {
        Integer productId = 1;
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
        Assertions.assertEquals(product.getTitle(), updatedProduct.getTitle());
    }

    //    get single Product
    @Test
    public void getSingleProduct_Test() {
        Integer productId = 1;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));

        ProductDto productDto = this.productService.getSingle(productId);
        System.out.println(productDto.getTitle());
        Assertions.assertNotNull(productDto);
    }

    //    delete Product
    @Test
    public void deleteProduct_Test() {
        Integer productId = 6;
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(of(product));

        this.productService.delete(productId);

        Mockito.verify(repository, Mockito.timeout(1)).delete(product);

    }

    //    Get All Product
    @Test
    public void getAllProduct_Test() {
        Product product = Product.builder()
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

        Product product1 = Product.builder()
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

        Product product2 = Product.builder()
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

        List<Product> allUsers = Arrays.asList(product, product1, product2);
        Page<Product> page = new PageImpl<>(allUsers);
        Mockito.when(this.repository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> dto = this.productService.getAll(2, 3, "title", "asc");
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, dto.getContent().size());
    }

    //    create With category
    @Test
    public void createWithCategoryProduct_Test()
    {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(product);
        Mockito.when(this.categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(category));
        ProductDto dto = this.mapper.map(product, ProductDto.class);
        Assertions.assertNotNull(dto);
        System.out.println(dto.getTitle());
        Assertions.assertEquals("Iphone 13",dto.getTitle());
    }

//    get All Live products
    @Test
    public void getAllAliveProduct_Test()
    {
        Product product = Product.builder()
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

        Product product1 = Product.builder()
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

        Product product2 = Product.builder()
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

        List<Product> allProduct= Arrays.asList(product,product1,product2);
        Page<Product> page= new PageImpl<> (allProduct);
        Mockito.when(this.repository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> dto = this.productService.getAllLive(1, 2, "title", "asc");
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3,dto.getContent().size());
    }

//    search Products
    @Test
    public void searchProduct_Test()
    {
        Product product = Product.builder()
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

        Product product1 = Product.builder()
                .title("Samsung S22 Ultra")
                .description("This Is Samsung Company Best Product Testing")
                .price(100000)
                .discountedPrice(90000)
                .productImageName("samsung.png")
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();

        Product product2 = Product.builder()
                .title("Redmi 12 Pro 5G")
                .description("This Is Xiaomi Company Best Product Testing")
                .price(27000)
                .discountedPrice(22000)
                .productImageName("one.png")
                .quantity(20)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .build();

        String keyword="title";
        List<Product> allProduct=Arrays.asList(product,product1,product2);
        Page<Product> page= new PageImpl<>(allProduct);
        Mockito.when(this.repository.findByTitleContaining(Mockito.anyString(),Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> searched = this.productService.searchByTitleContaining(keyword, 1, 1, "title", "asc");

        Assertions.assertEquals(3,searched.getContent().size());
        Assertions.assertNotNull(searched);
    }
}
