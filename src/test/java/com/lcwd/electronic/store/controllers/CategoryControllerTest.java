package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.service.CategoryService;
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
public class CategoryControllerTest
{
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @BeforeEach
    public void init()
    {
         category= Category.builder()
                .title("Mobile")
                .description("This is Category module testing")
                .categoryImage("image.png")
                .build();
    }

    private String convetObjectToJsonString(Object category) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(category);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
//    create Category
    @Test
    public void createCategory_Test() throws Exception {
        CategoryDto dto = this.mapper.map(category, CategoryDto.class);

        Mockito.when(this.categoryService.createCategory(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convetObjectToJsonString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

//    update category
    @Test
    public void updateCategory_Test() throws Exception
    {
        Integer catId=1;
        CategoryDto dto = this.mapper.map(category, CategoryDto.class);

        Mockito.when(this.categoryService.updateCategory(Mockito.any(),Mockito.anyInt())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/cat/"+catId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convetObjectToJsonString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

//    get single Category
    @Test
    public void getSingleCategory_Test() throws Exception {
        CategoryDto dto = this.mapper.map(category, CategoryDto.class);
        Integer catId=12;

        Mockito.when(this.categoryService.getSingleCategory(Mockito.anyInt())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/cat/"+catId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
