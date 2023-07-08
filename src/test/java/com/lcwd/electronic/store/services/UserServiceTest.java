package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    private User user;

    private UserDto userDto;

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

        UserDto user1 = this.userService.saveUser(mapper.map(user, UserDto.class));

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

        UserDto user1 = this.userService.updateUser(userDto, user.getUserId());

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

        this.userService.deleteUser(userId);

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

        List<UserDto> allUsers = this.userService.getAllUsers();

    }

//    gte Single method
    @Test
    public void getSingleUser_Test()
    {
        Integer userId= 1;
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));

        UserDto user1 = this.userService.getSingleUserById(1);

        Assertions.assertNotNull(user1);

        Assertions.assertEquals(user.getName(),user1.getName());

        System.out.println(user1);

    }


//    search User
    @Test
    public void searchUser_Test()
    {
        User user1=user = User.builder()
                .name("Pankaj Thakre")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("pankaj@gmail.com")
                .password("Pankaj4455")
                .imageName("pankaj.png")
                .build();

        User user2=user = User.builder()
                .name("Vaibhav Kumar")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("vaibhavK@gmail.com")
                .password("Pankaj4455")
                .imageName("vaibhav.png")
                .build();

        User user3=user = User.builder()
                .name("Ashish Kumar Mishra")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("ashu@gmail.com")
                .password("ashu12")
                .imageName("ash.png")
                .build();

        User user4=user = User.builder()
                .name("Kartik Kumar")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("kartik@gmail.com")
                .password("kartik78")
                .imageName("krtik.png")
                .build();

        String keyword="name";

        Mockito.when(this.userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1,user2,user3,user4));
        List<UserDto> userDto = this.userService.searchUser(keyword);
        Assertions.assertNotNull(userDto);
        System.out.println(userDto);
        Assertions.assertEquals(4,userDto.size());
    }

//    get Email
    @Test
    public void getUserByEmail_Test()
    {
        String emailId="pankajthakare@gmail.com";

        Mockito.when(this.userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto user1 = this.userService.getUserByEmail(emailId);

        System.out.println(user1.getEmail());
        Assertions.assertEquals(user.getEmail(),user1.getEmail());
    }

//    get All By Pagging And Sorting
    @Test
    public void getAllByPagination_Test()
    {
        User user1=user = User.builder()
                .name("Pankaj Thakre")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("pankaj@gmail.com")
                .password("Pankaj4455")
                .imageName("pankaj.png")
                .build();

        User user2=user = User.builder()
                .name("Vaibhav Kumar")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("vaibhavK@gmail.com")
                .password("Pankaj4455")
                .imageName("vaibhav.png")
                .build();

        User user3=user = User.builder()
                .name("Ashish Kumar Mishra")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("ashu@gmail.com")
                .password("ashu12")
                .imageName("ash.png")
                .build();

        User user4=user = User.builder()
                .name("Kartik Kumar")
                .gender("Male")
                .about("This is Testing Method for update User")
                .email("kartik@gmail.com")
                .password("kartik78")
                .imageName("krtik.png")
                .build();

        List<User> alluser= Arrays.asList(user1,user2,user3,user4);

        Page<User> page= new PageImpl<>(alluser);

        Mockito.when(this.userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> listOfUsers = this.userService.getAllUsersBySorting(2, 1, "name", "Asc");

        Assertions.assertEquals(4,listOfUsers.getContent().size());

        System.out.println(listOfUsers.getContent());
    }
}
