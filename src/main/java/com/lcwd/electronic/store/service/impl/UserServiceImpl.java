package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserServiceI;
import com.lcwd.electronic.store.utility.Helper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    private String imagePath;

    /**
     * @authar Pankaj
     * @implNote  This process is use Implementing for the save user Details
     * @param userDto
     * @return
     */
    @Override
    public UserDto saveUser(UserDto userDto)
    {
        logger.info("Initiating dao call for save the User details");
        User user = this.mapper.map(userDto, User.class);

        User saveuser = this.userRepository.save(user);

        UserDto userDto1 = this.mapper.map(saveuser, UserDto.class);
        logger.info("Completed dao call for save the User details");
        return userDto1;
    }

    /**
     * @implNote This process is use Implementing for the update user Details
     * @param userDto
     * @param userId
     * @return
     */
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId)
    {
        logger.info("Initiating dao call for update the User details with id:{}",userId);
        User user = this.mapper.map(userDto, User.class);

        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User saveUser = this.userRepository.save(user);
        logger.info("Completed dao call for update the User details with id:{}",userId);
        return this.mapper.map(saveUser,UserDto.class);
    }

    /**
     * @implNote This process is use Implementing for the delete user Details
     * @param userId
     */
    @Override
    public void deleteUser(Integer userId)
    {
        logger.info("Initiating dao call for delete the User details with id:{}",userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        String fullPath = imagePath + user.getImageName();

        try
        {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex)
        {
            ex.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        this.userRepository.delete(user);
        logger.info("Completed dao call for delete the User details with id:{}",userId);
    }

    /**
     * @implNote This process is use Implementing for the get Single user Details
     * @param userId
     * @return
     */
    @Override
    public UserDto getSingleUserById(Integer userId)
    {
        logger.info("Initiating dao call for get the Single User details with id:{}",userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        UserDto userDto = this.mapper.map(user, UserDto.class);
        logger.info("Completed dao call for get the Single User details with id:{}",userId);
        return userDto;
    }

    /**
     * @implNote This process is use Implementing for the get By Email user Details
     * @param email
     * @return
     */
    @Override
    public UserDto getUserByEmail(String email)
    {
        logger.info("Initiating dao call for get the User details with email id:{}",email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        UserDto userDto = this.mapper.map(user, UserDto.class);
        logger.info("Completed dao call for get the User details with email id:{}",email);
        return userDto;
    }

    /**
     * @implNote This process is use Implementing for the get All user Details
     * @return
     */
    @Override
    public List<UserDto> getAllUsers()
    {

        logger.info("Initiating dao call for get All the User details");
        List<User> allusers = this.userRepository.findAll();
        List<UserDto> UsersList = allusers.stream().map((users) -> this.mapper.map(users, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed dao call for get All the User details");
        return UsersList;
    }

    /**
     * @implNote This process is use Implementing for the Search User Details
     * @param keyword
     * @return
     */
    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao call for Search User details with :{}",keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map((user) -> this.mapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Initiating dao call for Search User details with :{}",keyword);
        return userDtos;
    }

    /**
     * @implNote This process is use Implementing for the getAll Users By Sorting and Pagination
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDi
     * @return
     */
    @Override
    public PageableResponse<UserDto> getAllUsersBySorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDi)
    {
         logger.info("Initiating dao call for get All the User details By Sorting Page and Order");
        Sort sort=(sortDi.equalsIgnoreCase("dsc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = this.userRepository.findAll(pageable);
        List<User> allusers = page.getContent();

        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
        logger.info("Completed dao call for get All the User details By Sorting Page and Order");
        return response;
    }
}
