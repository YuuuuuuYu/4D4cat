package com.example.springbootmustache.nakji;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(NakjiController.class)
public class mockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mockMvcTest() throws Exception {
        searchExample ex = new searchExample();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/nakji/convert")
                        .contentType("application/json")
                        .content("{\"title\":\"Sample Page\",\"content\":\"Sample Content\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Sample Page"))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Sample Content"));


    }


}
