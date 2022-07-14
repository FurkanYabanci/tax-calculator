package com.yabanci.ayrotek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yabanci.ayrotek.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotek.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotek.model.ProductType;
import com.yabanci.ayrotek.service.TaxService;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TaxControllerTest extends BaseControllerTest{

    private static final String BASE_PATH = "/api/v1/taxes";

    private MockMvc mockMvc;

    @MockBean
    private TaxService taxService;

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
        TaxSaveRequestDto taxSaveRequestDto = new TaxSaveRequestDto();
        taxSaveRequestDto.setRate(BigDecimal.TEN);
        taxSaveRequestDto.setProductType(ProductType.FOOD);

        String content = objectMapper.writeValueAsString(taxSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void update() throws Exception {
        TaxUpdateRequestDto taxUpdateRequestDto = new TaxUpdateRequestDto();
        taxUpdateRequestDto.setRate(BigDecimal.TEN);
        taxUpdateRequestDto.setProductType(ProductType.STATIONERY);
        taxUpdateRequestDto.setId(Long.valueOf(1));

        String content = objectMapper.writeValueAsString(taxUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_PATH+"/78")
                        .content("78")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}