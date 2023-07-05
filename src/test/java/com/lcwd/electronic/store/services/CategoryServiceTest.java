package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ModelMapper mapper;

    Category category;

    CategoryDto categoryDto;
    @BeforeEach
    public void init()
    {
        category =Category.builder()
                .title("Mobiles")
                .description("This is Mobile Testing Create Method")
                .categoryImage("abc.png")
                .build();
    }

//    create Category
    @Test
    public void createCategory_Test()
    {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(category);

        CategoryDto category1 = this.categoryService.createCategory(mapper.map(category, CategoryDto.class));

        Assertions.assertNotNull(category1);
    }
}
