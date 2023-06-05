package com.lcwd.electronic.store.servicei;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;

import java.util.List;
import java.util.Optional;

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
}
