package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger logger= LoggerFactory.getLogger(ProductController.class);

    //    create

    /**
     * @Auher Pankaj
     * @apiNote This Api Is use for the save Product details
     * @param productDto
     * @return
     */
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        logger.info("Initiate the request for Save the Product Details");
        ProductDto productDto1 = this.productService.create(productDto);
        logger.info("Completed the request for Save the Product Details");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    //    update
    /**
     * @apiNote This Api Is Use for the update Product details
     * @param productId
     * @param productDto
     * @return
     */
    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer productId,@RequestBody ProductDto productDto)
    {
        logger.info("Initiate the request for Update the Product Details with id:{}",productId);
        ProductDto updated = this.productService.update(productDto, productId);
        logger.info("Completed the request for Update the Product Details with id:{}",productId);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }


    //    delete
    /**
     * @apiNote This Api Is use for the Delete the Product Details
     * @param productId
     * @return
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Integer productId)
    {
        logger.info("Initiate the request for delete the Product Details with id:{}",productId);
        this.productService.delete(productId);
        logger.info("Completed the request for delete the Product Details with id:{}",productId);
        return new ResponseEntity(new ApiResponse(AppConstant.PRODUCT_DELETE,true),HttpStatus.OK);
    }

    //    get Single
    /**
     * @apiNote This Api Is Use for the get Single Product details
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Integer productId)
    {
        logger.info("Initiate the request for the get Single Product Details with id:{}",productId);
        ProductDto singleProduct = this.productService.getSingle(productId);
        logger.info("Completed the request for the get Single Product Details with id:{}",productId);
        return new ResponseEntity<>(singleProduct,HttpStatus.OK);
    }

//    get All

    /**
     * @apiNote This Api Is use for the Get All The Product Details
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct
    (
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    )
    {
        logger.info("Initiate the request for the get All Product Details");
        PageableResponse<ProductDto> response = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for the get All Product Details");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    get All live

    /**
     * @apiNote This Api is used For the get All Live products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/liveproducts")
public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct
(
        @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
)
{
    logger.info("Initiate the request for the get All Live Product Details");
    PageableResponse<ProductDto> response = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
    logger.info("Completed the request for the get All Live Product Details");
    return new ResponseEntity<>(response,HttpStatus.OK);
}

//    search

    /**
     * @apiNote This Api Is use for search All the Products
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/search/{subTitle}")
    public  ResponseEntity<PageableResponse<ProductDto>> searchProduct
    (
            @PathVariable String subTitle,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    )
    {
        logger.info("Initiate the request for the Search Product Details with title:{}",subTitle);
        PageableResponse<ProductDto> productResponse = this.productService.searchByTitleContaining(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for the Search Product Details with title:{}",subTitle);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

}
