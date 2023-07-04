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
}
