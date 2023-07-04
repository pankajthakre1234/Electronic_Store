package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.utility.Helper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    private String imagePath;

    /**
     * @Auther Pankaj
     * @implNote This Process is Implementing The Create Category Details
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto)
    {
        logger.info("Initiating dao call for save the category details");
        Category category = this.mapper.map(categoryDto, Category.class);
        Category saveCat = this.repository.save(category);
        logger.info("Completed dao call for save the category details");
        return this.mapper.map(saveCat,CategoryDto.class);
    }

    /**
     * @implNote This Process is Implementing The Update Category Details
     * @param categoryDto
     * @param catId
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId)
    {
        logger.info("Initiating dao call for Update the category details with id :{}",catId);
        Category categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "cateId", catId));
        categories.setTitle(categoryDto.getTitle());
        categories.setDescription(categoryDto.getDescription());
        categories.setCategoryImage(categoryDto.getCategoryImage());

        Category saveCategory = this.repository.save(categories);
        logger.info("Completed dao call for Update the category details with id :{}",catId);
        return this.mapper.map(saveCategory,CategoryDto.class);
    }

    /**
     * @implNote This Process is Implementing The Get All Category Details
     * @return
     */
    @Override
    public List<CategoryDto> getAllCategorys()
    {
        logger.info("Initiating dao call for get All the category details");
        List<Category> allcat = this.repository.findAll();
        List<CategoryDto> dtoList = allcat.stream().map((user) -> this.mapper.map(user, CategoryDto.class)).collect(Collectors.toList());
        logger.info("Completed dao call for get All the category details");

        return dtoList;
    }

    /**
     * @implNote This Process is Implementing The Delete Category Details
     * @param catId
     */
    @Override
    public void deleteCategory(Integer catId)
    {
        logger.info("Initiating dao call for Delete the category details with id :{}",catId);

        Category categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "catId", catId));

        String fullPath = imagePath + categories.getCategoryImage();

        try
        {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex)
        {
            ex.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        this.repository.delete(categories);
        logger.info("Completed dao call for Delete the category details with Id :{}",catId);
    }

    /**
     * @implNote This Process is Implementing The Get Single Category Details
     * @param catId
     * @return
     */
    @Override
    public CategoryDto getSingleCategory(Integer catId)
    {
        logger.info("Initiating dao call for get the Single category details with Id :{}",catId);
        Category categories = this.repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Categories", "catId", catId));
        logger.info("Completed dao call for get the Single category details with Id :{}",catId);
        return this.mapper.map(categories,CategoryDto.class);
    }

    /**
     * @implNote This Process is Implementing The get All Category Details by Using Pagination And Sorting
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        logger.info("Initiating dao call for get All category details with Sorting Pagination And Order");
        Sort sort=(sortDir.equalsIgnoreCase("dsc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = this.repository.findAll(pageable);
        List<Category> categories = page.getContent();

        PageableResponse pageableResponse= Helper.getPageableResponse(page,CategoryDto.class);
        logger.info("Completed dao call for get All category details with Sorting Pagination And Order");
        return pageableResponse;
    }
}
