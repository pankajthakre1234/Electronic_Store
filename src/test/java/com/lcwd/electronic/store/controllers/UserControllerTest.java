package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.helper.ApiResponse;
import com.lcwd.electronic.store.helper.PageableResponse;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.DoNotMock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private FileService fileService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @Value("${user.profile.image.path}")
    public String imageUploadPath;


    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Pankaj")
                .email("pankaj45@gmail.com")
                .about("This is Create user Testing")
                .gender("Male")
                .imageName("abc.jpg")
                .password("pankaj12")
                .build();

    }

    private String convetObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

//    create user

    @Test
    public void createUser_Test() throws Exception {
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(this.userService.saveUser(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convetObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    //    update user
    @Test
    public void updateUser_Test() throws Exception {
        Integer userId = 12;
        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(this.userService.updateUser(Mockito.any(), Mockito.anyInt())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convetObjectToJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    //    get Single User
    @Test
    public void getSingleUser_Test() throws Exception {
        Integer userId = 2;

        UserDto userDto = this.mapper.map(user, UserDto.class);

        Mockito.when(this.userService.getSingleUserById(Mockito.any())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    //    get All users
    @Test
    public void getAllUsers_Test() throws Exception {
        UserDto userDto = this.mapper.map(user, UserDto.class);
        UserDto userDto1 = UserDto.builder().name("Pankaj").email("pankajthakre@gmail.com").gender("male").password("pankaj1").about("This testing is for get All users").build();
        UserDto userDto2 = UserDto.builder().name("Ashish").email("ashish@gmail.com").gender("male").password("ashsih").about("This testing is for get All users").build();
        UserDto userDto3 = UserDto.builder().name("Amrut").email("amrut@gmail.com").gender("male").password("amrutt").about("This testing is for get All users").build();
        UserDto userDto4 = UserDto.builder().name("Ramesh").email("ramesh@gmail.com").gender("male").password("ramesh").about("This testing is for get All users").build();

        List<UserDto> userList = Arrays.asList(userDto1, userDto2, userDto3, userDto4);
        Mockito.when(this.userService.getAllUsers()).thenReturn(userList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


//    delete user
    @Test
    public void deleteUser_Test() throws Exception
    {
        Integer userId=20;
        this.userService.deleteUser(userId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


    //   get user BYEmail
    @Test
    public void getUserByEmail_Test() throws Exception {
        UserDto userDto = this.mapper.map(user, UserDto.class);
        String email = "pankajthakre@gmail.com";
        Mockito.when(this.userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    search user
    @Test
    public void searchUser_Test() throws Exception {
        UserDto userDto1 = UserDto.builder().name("Pankaj").email("pankajthakre@gmail.com").gender("male").password("pankaj1").about("This testing is for get All users").build();
        UserDto userDto2 = UserDto.builder().name("Ashish").email("ashish@gmail.com").gender("male").password("ashish").about("This testing is for get All users").build();
        UserDto userDto3 = UserDto.builder().name("Amrut").email("amrut@gmail.com").gender("male").password("amrutt").about("This testing is for get All users").build();
        UserDto userDto4 = UserDto.builder().name("Ramesh").email("ramesh@gmail.com").gender("male").password("ramesh").about("This testing is for get All users").build();

        List<UserDto> allUsers = Arrays.asList(userDto1, userDto2, userDto3, userDto4);
        String keyword = "Pankaj";
        Mockito.when(this.userService.searchUser(Mockito.anyString())).thenReturn(allUsers);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/search/" + keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    get all users
    @Test
    public void getAllUsersByPaginationAndSorting_Test() throws Exception {
        UserDto userDto = UserDto.builder().name("Pankaj").email("pankajthakre@gmail.com").gender("male").password("pankaj1").about("This testing is for get All users").build();
        UserDto userDto1 = UserDto.builder().name("Ashish").email("ashish@gmail.com").gender("male").password("ashish").about("This testing is for get All users").build();
        UserDto userDto2 = UserDto.builder().name("Amrut").email("amrut@gmail.com").gender("male").password("amrutt").about("This testing is for get All users").build();
        UserDto userDto3 = UserDto.builder().name("Ramesh").email("ramesh@gmail.com").gender("male").password("ramesh").about("This testing is for get All users").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(userDto, userDto1, userDto2, userDto3));
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(5);
        pageableResponse.setTotalPages(100);
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(10000l);

        Mockito.when(this.userService.getAllUsersBySorting(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

