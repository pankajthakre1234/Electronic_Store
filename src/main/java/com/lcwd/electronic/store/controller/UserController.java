package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.AppConstant;
import com.lcwd.electronic.store.servicei.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    Logger logger= LoggerFactory.getLogger(UserController.class);

//     create User
    /**
     * @Author Pankaj
     * @apiNote This Api is Use For The Create Users Details
     * @param userDto
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        logger.info("Initiate the request for Save the User Details");
        UserDto userDto1 = this.userServiceI.saveUser(userDto);
        logger.info("Completed the request for Save the User Details");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

//    update user detail
    /**
     * @apiNote This Api Is Use For The Update User Details
     * @param userDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
        logger.info("Initiate the request for Update the User Details");
        UserDto userDto1 = this.userServiceI.updateUser(userDto, userId);
        logger.info("Completed the request for Update the User Details");
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

//    get Single Users details
    /**
     * @apiNote This Api Is Use For The Get Single User By Id
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
    {
        logger.info("Initiate the request for Get the Single User Details");
        UserDto userById = this.userServiceI.getSingleUserById(userId);
        logger.info("Completed the request for Get the Single User Details");
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

//    get All users Details
    /**
     * @apiNote This Api Is Use For The Get All Users Details
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser()
    {
        logger.info("Initiate the request for Get the All User Details");
        List<UserDto> allUsers = this.userServiceI.getAllUsers();
        logger.info("Completed the request for Get the All User Details");
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

//    delete user Details
    /**
     * @apiNote This Api Is Use For The Delete The User
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
    {
        logger.info("Initiate the request for Delete the User Details");
        this.userServiceI.deleteUser(userId);
        logger.info("Completed the request for Delete the User Details");
        return new ResponseEntity(new ApiResponse(AppConstant.USER_DELETE,false),HttpStatus.OK);
    }

//    get User By email
    /**
     * @apiNote This Api Is Use For The Get User By Email
     * @param email
     * @return
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        logger.info("Initiate the request for Get the User By Email");
        UserDto userByEmail = this.userServiceI.getUserByEmail(email);
        logger.info("Completed the request for Get the User By Email");
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

//    get user searching
    /**
     * @apiNote This Api Is Use For The Search User By Keyword
     * @param keyword
     * @return
     */
    @GetMapping("/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword)
    {
        logger.info("Initiate the request for the Search User By Keyword");
        List<UserDto> userDto = this.userServiceI.searchUser(keyword);
        logger.info("Completed the request for the Search User By Keyword");
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

//    get all Users by Pagination and Sorting

    /**
     * @apiNote This Api is Use for The get All Users By Sorting and Pagination
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    public ResponseEntity<List<UserDto>> getAllUsersBySorting(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "dirBY", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)
    {
        logger.info("Initiate the request for Get the All User Details by Sorting and Pagging");
        List<UserDto> users = this.userServiceI.getAllUsersBySorting(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for Get the All User Details by Sorting and Pagging");
        return new ResponseEntity<List<UserDto>>(users,HttpStatus.OK);
    }
}
