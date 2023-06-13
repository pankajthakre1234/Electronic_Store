package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.helper.ImageResponse;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserServiceI;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    public String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

//     create User

    /**
     * @Author Pankaj
     * @param userDto
     * @return
     * @apiNote This Api is Use For The Create Users Details
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Initiate the request for Save the User Details");
        UserDto userDto1 = this.userServiceI.saveUser(userDto);
        logger.info("Completed the request for Save the User Details");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

//    update user detail

    /**
     * @param userDto
     * @param userId
     * @return
     * @apiNote This Api Is Use For The Update User Details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
        logger.info("Initiate the request for Update the User Details with userId :{}",userId);
        UserDto userDto1 = this.userServiceI.updateUser(userDto, userId);
        logger.info("Completed the request for Update the User Details with userId:{}",userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

//    get Single Users details

    /**
     * @param userId
     * @return
     * @apiNote This Api Is Use For The Get Single User By Id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        logger.info("Initiate the request for Get the Single User Details with userId :{}",userId);
        UserDto userById = this.userServiceI.getSingleUserById(userId);
        logger.info("Completed the request for Get the Single User Details with userId :{}",userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

//    get All users Details

    /**
     * @return
     * @apiNote This Api Is Use For The Get All Users Details
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        logger.info("Initiate the request for Get the All User Details");
        List<UserDto> allUsers = this.userServiceI.getAllUsers();
        logger.info("Completed the request for Get the All User Details");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

//    delete user Details

    /**
     * @param userId
     * @return
     * @apiNote This Api Is Use For The Delete The User
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        logger.info("Initiate the request for Delete the User Details with userId :{}",userId);
        this.userServiceI.deleteUser(userId);
        logger.info("Completed the request for Delete the User Details  with userId :{}",userId);
        return new ResponseEntity(new ApiResponse(AppConstant.USER_DELETE, false), HttpStatus.OK);
    }

//    get User By email

    /**
     * @param email
     * @return
     * @apiNote This Api Is Use For The Get User By Email
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Initiate the request for Get the User By Email :{}",email);
        UserDto userByEmail = this.userServiceI.getUserByEmail(email);
        logger.info("Completed the request for Get the User By Email :{}",email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

//    get user searching

    /**
     * @param keyword
     * @return
     * @apiNote This Api Is Use For The Search User By Keyword
     */
    @GetMapping("/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Initiate the request for the Search User By Keyword");
        List<UserDto> userDto = this.userServiceI.searchUser(keyword);
        logger.info("Completed the request for the Search User By Keyword");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    get all Users by Pagination and Sorting

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @apiNote This Api is Use for The get All Users By Sorting and Pagination
     */
    @GetMapping("/allusers")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsersBySorting(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("Initiate the request for Get the All User Details by Sorting and Pagging");
        PageableResponse<UserDto> users = this.userServiceI.getAllUsersBySorting(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for Get the All User Details by Sorting and Pagging");
        return new ResponseEntity<PageableResponse<UserDto>>(users, HttpStatus.OK);
    }

    //    upload user image

    /**
     * @apiNote This Api is Use for the Upload The Image
     * @param userId
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable Integer userId, @RequestParam("userImage") MultipartFile image) throws IOException
    {
        logger.info("Initiate the request for Upload the User Image with userId :{}",userId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);

        UserDto user = this.userServiceI.getSingleUserById(userId);
        user.setImageName(imageName);

        UserDto userDto = this.userServiceI.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().message(AppConstant.IMAGE_UPLOADED).imageName(imageName).success(true).build();
        logger.info("Completed the request for Upload the User Image with userId :{}",userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

//    serve user image

    /**
     * @apiNote This Api is Use for the serve the Image
     * @param userId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable Integer userId, HttpServletResponse response) throws IOException
    {
        logger.info("Initiate the request for serve the User Image with userId :{}",userId);
        UserDto user = this.userServiceI.getSingleUserById(userId);

        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Completed the request for serve the User Image with userId :{}",userId);
    }
}
