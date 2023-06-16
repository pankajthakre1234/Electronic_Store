package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto create(ProductDto productDto)
    {
        logger.info("Initiating dao call for save the Product details");
        Product product = this.mapper.map(productDto, Product.class);

        Product saveProduct = this.repository.save(product);
        logger.info("Completed dao call for save the Product details");
        return this.mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Integer productId)
    {
        logger.info("Initiating dao call for update the Product details with id :{}",productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        logger.info("Completed dao call for update the Product details with id :{}",productId);
        return this.mapper.map(product, ProductDto.class);
    }

    @Override
    public void delete(Integer productId)
    {
        logger.info("Initiating dao call for delete the Product details with id :{}",productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        logger.info("Completed dao call for delete the Product details with id :{}",productId);
        this.repository.delete(product);
    }

    @Override
    public ProductDto getSingle(Integer productId)
    {
        logger.info("Initiating dao call for get Single Product details with id :{}",productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        logger.info("Completed dao call for get Single Product details with id :{}",productId);
        return this.mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        logger.info("Initiating dao call for get All Product details");
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findAll(pageable);

        List<Product> productList = page.getContent();

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed dao call for get All Product details");
        return response;
    }


    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        logger.info("Initiating dao call for get All Live Product details");
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findByLiveTrue(pageable);

        List<Product> productList1 = page.getContent();
        List<Product> productList = productList1;

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed dao call for get All Live Product details");
        return response;
    }

    @Override
      public PageableResponse<ProductDto> searchByTitleContaining(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        logger.info("Initiating dao call for Search Product details");
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findByTitleContaining(subTitle, pageable);

        List<Product> productList1 = page.getContent();
        List<Product> productList = productList1;

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed dao call for Search Product details");
        return response;
    }
}
