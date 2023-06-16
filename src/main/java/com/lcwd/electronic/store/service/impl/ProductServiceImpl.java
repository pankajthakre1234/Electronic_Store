package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import com.lcwd.electronic.store.utility.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto)
    {
        Product product = this.mapper.map(productDto, Product.class);

        Product saveProduct = this.repository.save(product);

        return this.mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Integer productId)
    {
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        return this.mapper.map(product, ProductDto.class);
    }

    @Override
    public void delete(Integer productId)
    {
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        this.repository.delete(product);
    }

    @Override
    public ProductDto getSingle(Integer productId) {
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        return this.mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findAll(pageable);

        List<Product> productList = page.getContent();

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);

        return response;
    }


    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findByLiveTrue(pageable);

        List<Product> productList1 = page.getContent();
        List<Product> productList = productList1;

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);

        return response;
    }

    @Override
      public PageableResponse<ProductDto> searchByTitleContaining(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findByTitleContaining(subTitle, pageable);

        List<Product> productList1 = page.getContent();
        List<Product> productList = productList1;

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);

        return response;
    }
}
