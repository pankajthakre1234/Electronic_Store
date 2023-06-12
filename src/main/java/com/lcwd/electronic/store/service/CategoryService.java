package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.helper.PageableResponse;

import java.util.List;

public interface CategoryService {

    //    create category
    CategoryDto createCategory(CategoryDto categoryDto);

    //    update category
    CategoryDto updateCategory(CategoryDto categoryDto,Integer catId);

    //    get all category
    List<CategoryDto> getAllCategorys();

    //    delete category
    void deleteCategory(Integer catId);

    //    get single category
    CategoryDto getSingleCategory(Integer catId);

    //  get all by Pageable
    PageableResponse<CategoryDto> getAllCategorys(int pageNumber, int pageSize, String sortBy, String sortDir);

}
