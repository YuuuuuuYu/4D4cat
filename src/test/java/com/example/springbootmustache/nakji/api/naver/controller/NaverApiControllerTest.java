package com.example.springbootmustache.nakji.api.naver.controller;

import com.example.springbootmustache.nakji.api.naver.service.NaverApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NaverApiControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private NaverApiController apiController;

    @Mock
    private NaverApiService apiService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    @DisplayName("성공하는 네이버 검색 호출 케이스")
    void naverSearchApi() throws Exception {
        // given
        String query = "bag";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/nakji/api/naver/search")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
        System.out.println("Result ::" + mvcResult.getResponse().getContentAsString());
    }
}