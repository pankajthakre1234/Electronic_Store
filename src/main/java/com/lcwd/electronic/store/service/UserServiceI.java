package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.helper.PageableResponse;

import java.util.List;

public interface UserServiceI {

    //create user
     UserDto saveUser(UserDto userDto);

    //update user
     UserDto updateUser(UserDto userDto,Integer userId);

    //delete user
     void deleteUser(Integer userId);

    //get Single user By id
    UserDto getSingleUserById(Integer userId);

    //get Single user By email
    UserDto getUserByEmail(String email);

    // get All user
     List<UserDto> getAllUsers();

   // search user
    List<UserDto> searchUser(String keyword);

    // get All users by pagination
     PageableResponse<UserDto> getAllUsersBySorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDi);
}
