package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Categories;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.utility.HelperPageable;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto)
    {
        Categories category = this.mapper.map(categoryDto, Categories.class);
        Categories saveCat = this.repository.save(category);

        return this.mapper.map(saveCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId)
    {
        Categories categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "cateId", catId));
        categories.setTitle(categoryDto.getTitle());
        categories.setDescription(categoryDto.getDescription());
        categories.setCategoryImage(categoryDto.getCategoryImage());

        Categories saveCategory = this.repository.save(categories);

        return this.mapper.map(saveCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategorys()
    {
        List<Categories> allcat = this.repository.findAll();
        List<CategoryDto> dtoList = allcat.stream().map((user) -> this.mapper.map(user, CategoryDto.class)).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public void deleteCategory(Integer catId)
    {
        Categories categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "catId", catId));
        this.repository.delete(categories);

    }

    @Override
    public CategoryDto getSingleCategory(Integer catId)
    {
        Categories categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "catId", catId));

        return this.mapper.map(categories,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort=(sortDir.equalsIgnoreCase("dsc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Categories> page = this.repository.findAll(pageable);
        List<Categories> content = page.getContent();

        PageableResponse pageableResponse= HelperPageable.getPageableResponse(page,CategoryDto.class);

        return pageableResponse;
    }
}
