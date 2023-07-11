package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.helper.ImageResponse;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest
{
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private FileService fileService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @Value("${category.profile.image.path}")
    public String imageUploadPath;

    @BeforeEach
    public void init()
    {
         category= Category.builder()
                .title("Electronics")
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

//    get all category
    @Test
    public void getAllCategory_Test() throws Exception
    {
        CategoryDto category1= CategoryDto.builder().title("Electronics").description("This is All electronics category Testing").categoryImage("ele.png").build();
        CategoryDto category2= CategoryDto.builder().title("Vehicle").description("This is All Vehicle category Testing").categoryImage("ele.png").build();
        CategoryDto category3= CategoryDto.builder().title("Airplane").description("This is All Airplane category Testing").categoryImage("ele.png").build();

        List<CategoryDto> dtoList = Arrays.asList(category1, category2, category3);
        Mockito.when(this.categoryService.getAllCategorys()).thenReturn(dtoList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categorys")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


//    delete category
    @Test
    public void deleteCategory_Test() throws Exception
    {
        Integer catId=10;
        this.categoryService.deleteCategory(catId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/cat/"+catId)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

//get All by Pagination and Sorting
    @Test
    public void getAllBYPagingAndSorting_Test() throws Exception
    {
        CategoryDto category1= CategoryDto.builder().title("Electronics").description("This is All electronics category Testing").categoryImage("ele.png").build();
        CategoryDto category2= CategoryDto.builder().title("Vehicle").description("This is All Vehicle category Testing").categoryImage("ele.png").build();
        CategoryDto category3= CategoryDto.builder().title("Mobiles").description("This is All Mobiles category Testing").categoryImage("ele.png").build();

        PageableResponse<CategoryDto> response=new PageableResponse<>();
        response.setContent(Arrays.asList(category1, category2, category3));
        response.setPageSize(10);
        response.setPageNumber(100);
        response.setTotalPages(200);
        response.setLastPage(false);
        response.setTotalElements(10000l);
        Mockito.when(this.categoryService.getAllCategories(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/allcategorys")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    upload image

}
