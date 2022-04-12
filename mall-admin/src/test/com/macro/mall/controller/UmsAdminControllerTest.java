package com.macro.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Using this class to test UmsAdminController.
 */
@DisplayName("Test for UmsAdminController")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UmsAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UmsAdminController umsAdminController;
    private static String USERNAME = "wanglikai";
    private static String PASSWORD = "password";

    @DisplayName("Test for UmsAdminController's create method.")
    @Test
    public void testCreateMethod() throws Exception {
        Map<String, String> credential = new HashMap<String, String>();
        credential.put("username", USERNAME);
        credential.put("password", PASSWORD);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/admin/login")
                .content(objectMapper.writeValueAsString(credential)))
                .andExpect(status().is2xxSuccessful())
                .andDo(result -> {
                    System.out.println("tokenHead: " + result.getResponse().getHeader("tokenHead"));
                    System.out.println("token: " + result.getResponse().getHeader("token"));
                });
    }
}
