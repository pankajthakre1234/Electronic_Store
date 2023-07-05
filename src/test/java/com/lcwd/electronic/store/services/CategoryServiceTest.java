package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.helper.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void init() {
        category = Category.builder()
                .title("Mobiles")
                .description("This is Mobile Testing Create Method")
                .categoryImage("abc.png")
                .build();
    }

    //    create Category
    @Test
    public void createCategory_Test() {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(category);

        CategoryDto category1 = this.categoryService.createCategory(mapper.map(category, CategoryDto.class));

        Assertions.assertNotNull(category1);
    }

    //    update category
    @Test
    public void updateCategory_Test() {
        categoryDto = CategoryDto.builder()
                .title("Cars")
                .description("This is Cars Testing Update Method")
                .categoryImage("abc.png")
                .build();

        Integer catId = 1;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(category));
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(category);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);

        Assertions.assertNotNull(updateCategory);

        Assertions.assertEquals(updateCategory.getTitle(), category.getTitle());
    }

    //    delete category
    @Test
    public void deleteCategory_Test() {
        Integer catId = 6;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));

        this.categoryService.deleteCategory(catId);

        Mockito.verify(repository, Mockito.timeout(1)).delete(category);
    }

//    get Single category
    @Test
    public void getSingleCategory_Test()
    {
        Integer catId=5;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));

        CategoryDto category1 = this.categoryService.getSingleCategory(catId);

        Assertions.assertNotNull(category1);

        System.out.println(category1.getDescription());
    }

//    get All category
    @Test
    public void getAllCategory_Test()
    {
        Category category1 = Category.builder()
                .title("Mobiles")
                .description("This is Mobile Testing Create Method")
                .categoryImage("abc.png")
                .build();

        Category category2 = Category.builder()
                .title("Cars")
                .description("This is Luxury Cars Testing Create Method")
                .categoryImage("cars.png")
                .build();

        Category category3=Category.builder()
                .title("Bikes")
                .description("This is Bikes Testing Create Method")
                .categoryImage("bike.png")
                .build();

        List<Category> all= Arrays.asList(category1,category2,category3);

        Mockito.when(this.repository.findAll()).thenReturn(all);

        List<CategoryDto> allCategorys = this.categoryService.getAllCategorys();

        Assertions.assertNotNull(allCategorys);

        Assertions.assertEquals(3,allCategorys.size());
    }

//    get All Pageable And Sorting
    @Test
    public void getAllPageabelCategory_Test()
    {
        Category category1 = Category.builder()
                .title("Mobiles")
                .description("This is Mobile Testing Create Method")
                .categoryImage("abc.png")
                .build();

        Category category2 = Category.builder()
                .title("Cars")
                .description("This is Luxury Cars Testing Create Method")
                .categoryImage("cars.png")
                .build();

        Category category3=Category.builder()
                .title("Bikes")
                .description("This is Bikes Testing Create Method")
                .categoryImage("bike.png")
                .build();

        List<Category> all=Arrays.asList(category1,category2,category3);

        Page<Category> page=new PageImpl<>(all);

        Mockito.when(this.repository.findAll((Pageable) Mockito.any())).thenReturn((page));

        PageableResponse<CategoryDto> allCategories = this.categoryService.getAllCategories(1, 2, "name", "Asc");

        Assertions.assertEquals(3,allCategories.getContent().size());
    }
}