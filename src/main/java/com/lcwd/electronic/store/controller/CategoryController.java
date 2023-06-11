package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService service;

    Logger logger= LoggerFactory.getLogger(CategoryController.class);

//    create
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto category = this.service.createCategory(categoryDto);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

//    update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
    {
        CategoryDto categoryDto1 = this.service.updateCategory(categoryDto, catId);

        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

//    get All
    @GetMapping("/categorys")
    public ResponseEntity<List<CategoryDto>> getAllCategory()
    {
        List<CategoryDto> allCategorys = this.service.getAllCategorys();

        return new ResponseEntity<>(allCategorys,HttpStatus.OK);
    }

//    get single
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer catId)
    {
        CategoryDto singleCategory = this.service.getSingleCategory(catId);

        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }

//    delete
    @DeleteMapping("/delete/{catId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Integer catId)
    {
        this.service.deleteCategory(catId);

        return new ResponseEntity(new ApiResponse(AppConstant.USER_DELETE,false), HttpStatus.OK);
    }

//    get pagination
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    )
    {
        PageableResponse<CategoryDto> response = this.service.getAllCategories(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
