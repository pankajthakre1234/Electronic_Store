package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.CategoryService;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * @param productDto
     * @return
     * @Auther Pankaj
     * @implNote This Process is Implementing for the save the Product details
     */
    @Override
    public ProductDto create(ProductDto productDto) {
        logger.info("Initiating dao call for save the Product details");
        Product product = this.mapper.map(productDto, Product.class);

        product.setAddedDate(new Date());
        Product saveProduct = this.repository.save(product);
        logger.info("Completed dao call for save the Product details");
        return this.mapper.map(saveProduct, ProductDto.class);
    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @implNote This Process is Implementing for the update the Product details
     */
    @Override
    public ProductDto update(ProductDto productDto, Integer productId) {
        logger.info("Initiating dao call for update the Product details with id :{}", productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setAddedDate(new Date());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product saveProduct = this.repository.save(product);

        logger.info("Completed dao call for update the Product details with id :{}", productId);
        return this.mapper.map(saveProduct, ProductDto.class);
    }

    /**
     * @param productId
     * @implNote This Process is Implementing for the delete the Product details
     */
    @Override
    public void delete(Integer productId) {
        logger.info("Initiating dao call for delete the Product details with id :{}", productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        logger.info("Completed dao call for delete the Product details with id :{}", productId);
        this.repository.delete(product);
    }

    /**
     * @param productId
     * @return
     * @implNote This Process is Implementing for the get Single Product details
     */
    @Override
    public ProductDto getSingle(Integer productId) {
        logger.info("Initiating dao call for get Single Product details with id :{}", productId);
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        logger.info("Completed dao call for get Single Product details with id :{}", productId);
        return this.mapper.map(product, ProductDto.class);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Process is Implementing for the get All the Product details
     */
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao call for get All Product details");
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.repository.findAll(pageable);

        List<Product> productList = page.getContent();

        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed dao call for get All Product details");
        return response;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Process is Implementing for the get All the live Product details
     */
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
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

    /**
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Process is Implementing for the Search the Product details
     */
    @Override
    public PageableResponse<ProductDto> searchByTitleContaining(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
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

    @Override
    public ProductDto createWithCategory(ProductDto productDto, Integer catId)
    {
        Category category = this.categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "catId", catId));

        Product product = this.mapper.map(productDto, Product.class);
        product.setCategory(category);
        Product savedProduct = this.repository.save(product);

        return this.mapper.map(savedProduct, ProductDto.class);
    }
}
