package com.yabanci.ayrotek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yabanci.ayrotek.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotek.dto.request.UserUpdateRequestDto;
import com.yabanci.ayrotek.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest extends BaseControllerTest{

    private static final String BASE_PATH = "/api/v1/users";

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_PATH)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void save() throws Exception {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setFirstName("Furkan");
        userSaveRequestDto.setLastName("Yabancı");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void update() throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setFirstName("Furkan");
        userUpdateRequestDto.setLastName("Yabancı");
        userUpdateRequestDto.setId(Long.valueOf(1));

        String content = objectMapper.writeValueAsString(userUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_PATH+"/55")
                        .content("55")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}