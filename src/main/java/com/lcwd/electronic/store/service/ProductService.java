package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.helper.PageableResponse;

import java.util.List;

public interface ProductService {

//    create
    ProductDto create(ProductDto productDto);

//    update
    ProductDto update(ProductDto productDto,Integer productId);

//    delete
    void delete(Integer productId);

//    get Single
    ProductDto getSingle(Integer productId);

//    get All
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

//    get All Live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

//    search product
    PageableResponse<ProductDto> searchByTitleContaining(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);


    ProductDto createWithCategory(ProductDto productDto,Integer catId);

}

