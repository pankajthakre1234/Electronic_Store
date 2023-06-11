package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Categories;
import com.lcwd.electronic.store.helper.PageableResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {


}
