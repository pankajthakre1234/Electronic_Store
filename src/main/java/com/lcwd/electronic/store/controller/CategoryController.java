package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.ImageResponse;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    public String imageUploadPath;

    Logger logger= LoggerFactory.getLogger(CategoryController.class);

//    create
    /**
     * @Auther Pankaj
     * @apiNote This Api Is use For The save The Category Details
     * @param categoryDto
     * @return
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        logger.info("Initiate the request for Save the Categories Details");
        CategoryDto category = this.service.createCategory(categoryDto);
        logger.info("Completed the request for Save the Categories Details");

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

//    update
    /**
     * @apiNote This Api Is use For The Update The Category Details
     * @param categoryDto
     * @param catId
     * @return
     */
    @PutMapping("/cat/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
    {
        logger.info("Initiate the request for Update the Categories Details with catId :{}",catId);
        CategoryDto categoryDto1 = this.service.updateCategory(categoryDto, catId);
        logger.info("Completed the request for Update the Categories Details with catId :{}",catId);

        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

//    get All
    /**
     * @apiNote This Api Is use For The Get All The Category Details
     * @return
     */
    @GetMapping("/categorys")
    public ResponseEntity<List<CategoryDto>> getAllCategory()
    {
        logger.info("Initiate the request for get All the Categories Details");
        List<CategoryDto> allCategorys = this.service.getAllCategorys();
        logger.info("Completed the request for get All the Categories Details");
        return new ResponseEntity<>(allCategorys,HttpStatus.OK);
    }

//    get single
    /**
     * @apiNote This Api Is use For The Get Single Category Details
     * @param catId
     * @return
     */
    @GetMapping("/cat/{catId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer catId)
    {
        logger.info("Initiate the request for get the Single Categories Details with catId :{}",catId);
        CategoryDto singleCategory = this.service.getSingleCategory(catId);
        logger.info("Completed the request for get the Single Categories Details with catId :{}",catId);
        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }

//    delete
    /**
     * @apiNote This Api Is use For The Delete the Category Details
     * @param catId
     * @return
     */
    @DeleteMapping("/cat/{catId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Integer catId)
    {
        logger.info("Initiate the request for Delete the Categories Details with catId :{}",catId);
        this.service.deleteCategory(catId);
        logger.info("Completed the request for Delete the Categories Details with catId :{}",catId);
        return new ResponseEntity(new ApiResponse(AppConstant.CATEGORY_DELETE,false), HttpStatus.OK);
    }

//    get pagination
    /**
     * @apiNote This Api Is use For The Get All Category Details by Using Pagination And Sorting
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/allcategorys")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    )
    {
        logger.info("Initiate the request for Get All the Categories Details with Pagination and Sorting");
        PageableResponse<CategoryDto> response = this.service.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for Get All the Categories Details with Pagination and Sorting");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    upload Cover Image
    /**
     * @apiNote This Api Is Use for the Upload Image
     * @param catId
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/catimage/{catId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@PathVariable Integer catId, @RequestParam("categoryImage") MultipartFile image) throws IOException
    {
        logger.info("Initiate the request for Upload the Category Image with catId :{}",catId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);

        CategoryDto category = this.service.getSingleCategory(catId);
        category.setCategoryImage(imageName);

        CategoryDto categoryDto = this.service.updateCategory(category, catId);

        ImageResponse imageResponse = ImageResponse.builder().message(AppConstant.IMAGE_UPLOADED).imageName(imageName).success(true).build();
        logger.info("Completed the request for Upload the Category Image with catId :{}",catId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

//    serve image
    /**
     * @apiNote This Api is Use For The Serve Cover image
     * @param catId
     * @param response
     * @throws IOException
     */
    @GetMapping("/catimage/{catId}")
    public void serveUserImage(@PathVariable Integer catId, HttpServletResponse response) throws IOException
    {
        logger.info("Initiate the request for serve the Category Image with userId :{}",catId);
        CategoryDto category = this.service.getSingleCategory(catId);

        InputStream resource = this.fileService.getResource(imageUploadPath, category.getCategoryImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Completed the request for serve the Category Image with userId :{}",catId);
    }


//    save products Details with categoryId

    /**
     * @apiNote This Api Is used for the save Products details with Category id
     * @param catId
     * @param productDto
     * @return
     */
    @PostMapping("/category/{catId}/products")
    public ResponseEntity<ProductDto> createWithCategory(
            @PathVariable Integer catId,
            @RequestBody ProductDto productDto
    )
    {
        logger.info("Initiate the request for the save The Products details with Category Id with catId :{}",catId);
        ProductDto createWithcategory = this.productService.createWithCategory(productDto, catId);
        logger.info("Completed the request for the save The Products details with Category Id with catId :{}",catId);
        return new ResponseEntity<>(createWithcategory,HttpStatus.CREATED);
    }
}
