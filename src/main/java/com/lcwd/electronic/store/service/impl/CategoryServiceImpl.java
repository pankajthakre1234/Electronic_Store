package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
        return null;
    }

    @Override
    public List<CategoryDto> getAllCategorys() {
        return null;
    }

    @Override
    public void deleteCategory(Integer catId) {

    }

    @Override
    public CategoryDto getSingleCategory(Integer catId) {
        return null;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
