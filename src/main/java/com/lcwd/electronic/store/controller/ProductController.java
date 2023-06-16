package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class ProductController {

    @Autowired
    private ProductService productService;

    //    create
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        ProductDto productDto1 = this.productService.create(productDto);

        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //    update
    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer productId,@RequestBody ProductDto productDto)
    {
        ProductDto updated = this.productService.update(productDto, productId);

        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    //    delete
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Integer productId)
    {
        this.productService.delete(productId);

        return new ResponseEntity(new ApiResponse(AppConstant.PRODUCT_DELETE,true),HttpStatus.OK);
    }

    //    get Single
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Integer productId)
    {
        ProductDto singleProduct = this.productService.getSingle(productId);

        return new ResponseEntity<>(singleProduct,HttpStatus.OK);
    }

//    get All
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct
    (
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> response = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    get All live
    @GetMapping("/liveproducts")
public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct
(
        @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
)
{
    PageableResponse<ProductDto> response = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);

    return new ResponseEntity<>(response,HttpStatus.OK);
}

//    search
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
        PageableResponse<ProductDto> productResponse = this.productService.searchByTitleContaining(subTitle, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

}
