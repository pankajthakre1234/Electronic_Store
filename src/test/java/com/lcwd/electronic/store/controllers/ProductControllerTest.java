package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest
{
    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    public void init()
    {
        product=Product.builder()
                .title("Iphone 13")
                .description("This Is Apple Company Products and Launched at 2022")
                .price(70000)
                .discountedPrice(65000)
                .stock(true)
                .live(true)
                .productImageName("apple.png")
                .build();
    }

    private String convetObjectToJsonString(Object product) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(product);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
//    create Product
    @Test
    public void createProduct_Test() throws Exception
    {
        ProductDto dto = this.mapper.map(product, ProductDto.class);

        Mockito.when(this.productService.create(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convetObjectToJsonString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

//    update Product
    @Test
    public void updateProduct_Test() throws Exception
    {
        Integer productId=10;
        ProductDto dto = this.mapper.map(product, ProductDto.class);
        Mockito.when(this.productService.update(Mockito.any(),Mockito.anyInt())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/product/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convetObjectToJsonString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

//    delete Product
    @Test
    public void deleteProduct_Test() throws Exception
    {
        Integer productId=5;
        this.productService.delete(productId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convetObjectToJsonString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

//    get Single Product
    @Test
    public void getSingleProduct_Test() throws Exception
    {
        Integer productId=2;
        ProductDto dto = this.mapper.map(product, ProductDto.class);
        Mockito.when(this.productService.getSingle(Mockito.anyInt())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



//get All Products
    @Test
    public void getAllProduct_Test() throws Exception
    {
        ProductDto productDto=ProductDto.builder().title("Iphone 13").description("This is Apple Company products").price(70000).discountedPrice(65000).live(true).stock(true).build();
        ProductDto productDto1=ProductDto.builder().title("Samsung s23 Ultra").description("This is Samsung Company products").price(95000).discountedPrice(93500).live(true).stock(true).build();
        ProductDto productDto2=ProductDto.builder().title("Redmi 12 pro 5G").description("This is Xiaomi Company products").price(26000).discountedPrice(24000).live(true).stock(true).build();
        ProductDto productDto3=ProductDto.builder().title("One Plus 11R 5G").description("This is One Plus Company products").price(40000).discountedPrice(37500).live(true).stock(true).build();

        PageableResponse<ProductDto> page=new PageableResponse<>();
        page.setContent(Arrays.asList(productDto,productDto1,productDto2,productDto3));
        page.setPageSize(20);
        page.setTotalPages(100);
        page.setLastPage(false);
        page.setTotalElements(100000l);
        page.setPageNumber(2);
        Mockito.when(this.productService.getAll(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    get All live Products
    @Test
    public void getAllLiveProduct_Test() throws Exception
    {
        ProductDto productDto=ProductDto.builder().title("Iphone 13").description("This is Apple Company products").price(70000).discountedPrice(65000).live(true).stock(true).build();
        ProductDto productDto1=ProductDto.builder().title("Samsung s23 Ultra").description("This is Samsung Company products").price(95000).discountedPrice(93500).live(false).stock(true).build();
        ProductDto productDto2=ProductDto.builder().title("Redmi 12 pro 5G").description("This is Xiaomi Company products").price(26000).discountedPrice(24000).live(false).stock(true).build();
        ProductDto productDto3=ProductDto.builder().title("One Plus 11R 5G").description("This is One Plus Company products").price(40000).discountedPrice(37500).live(true).stock(true).build();

        PageableResponse<ProductDto> page=new PageableResponse<>();
        page.setContent(Arrays.asList(productDto,productDto1,productDto2,productDto3));
        page.setPageSize(20);
        page.setTotalPages(100);
        page.setLastPage(false);
        page.setTotalElements(100000l);
        page.setPageNumber(2);

        Mockito.when(this.productService.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/live")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

//    search Products
    @Test
    public void searchProduct_Test() throws Exception
    {
        String subTitle="Iphone";
        ProductDto productDto=ProductDto.builder().title("Iphone 13").description("This is Apple Company products").price(70000).discountedPrice(65000).live(true).stock(true).build();
        ProductDto productDto1=ProductDto.builder().title("Samsung s23 Ultra").description("This is Samsung Company products").price(95000).discountedPrice(93500).live(false).stock(true).build();
        ProductDto productDto2=ProductDto.builder().title("Redmi 12 pro 5G").description("This is Xiaomi Company products").price(26000).discountedPrice(24000).live(false).stock(true).build();
        ProductDto productDto3=ProductDto.builder().title("One Plus 11R 5G").description("This is One Plus Company products").price(40000).discountedPrice(37500).live(true).stock(true).build();

        PageableResponse<ProductDto> page=new PageableResponse<>();
        page.setContent(Arrays.asList(productDto,productDto1,productDto2,productDto3));
        page.setPageSize(20);
        page.setTotalPages(100);
        page.setLastPage(false);
        page.setTotalElements(100000l);
        page.setPageNumber(2);

        Mockito.when(this.productService.searchByTitleContaining(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/searchproduct/"+subTitle)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
