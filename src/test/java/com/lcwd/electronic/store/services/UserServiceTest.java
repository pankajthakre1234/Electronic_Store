package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init()
    {
      user= User.builder()
                .name("Pankaj")
                .email("pankaj45@gmail.com")
                .about("This is Create user Testing")
                .gender("Male")
                .imageName("abc.jpg")
                .password("pankaj12")
                .build();
    }

//    create User testing

    @Test
    public void createUser_Test ()
    {
         Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = this.userServiceI.saveUser(mapper.map(user, UserDto.class));

        Assertions.assertNotNull(user1);
    }

//    update User
    @Test
    public void updateUser_Test()
    {
        UserDto userDto = UserDto.builder()
                .name("Pankaj Thakre")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("pankaj@gmail.com")
                .password("Pankaj4455")
                .imageName("pankaj.png")
                .build();

        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = this.userServiceI.updateUser(userDto, user.getUserId());

        Assertions.assertNotNull(user1);

        System.out.println(user1.getName());

        Assertions.assertEquals(user.getName(),user1.getName());
    }


//    delete Method
    @Test
    public void deleteUser_Test()
    {
        Integer userId = 1;

        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));

        this.userServiceI.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.timeout(1)).delete(user);
    }


//    get All Method
    @Test
    public void getAllUsers_Tset()
    {
        User user = User.builder()
                .name("Pankaj Thakre")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("pankaj@gmail.com")
                .password("Pankaj4455")
                .imageName("pankaj.png")
                .build();

        User user1 = User.builder()
                .name("Shailesh Kumar")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("shailesh@gmail.com")
                .password("shailesh4")
                .imageName("sha.png")
                .build();

        User user2 = User.builder()
                .name("Ashish Jadhav")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("Ashish@gmail.com")
                .password("Ashish12")
                .imageName("ashish.png")
                .build();

        List<User> allList= Arrays.asList(user,user1,user2);

        Mockito.when(this.userRepository.findAll((Sort) Mockito.any())).thenReturn(allList);

        List<UserDto> allUsers = this.userServiceI.getAllUsers();
    }

//    gte Single method
    @Test
    public void getSingleUser_Test()
    {
        Integer userId= 1;
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));

        UserDto user1 = this.userServiceI.getSingleUserById(userId);

        Assertions.assertNotNull(user1);
    }
}
