package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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


















}
