package com.macro.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static String TOKEN_HEAD = "Bearer ";
    private String token;

    @DisplayName("Test login method. Then gets token and tokenHead.")
    @BeforeEach
    public void testCreateMethod() throws Exception {
        Map<String, String> credential = new HashMap<String, String>();
        credential.put("username", USERNAME);
        credential.put("password", PASSWORD);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/admin/login")
                .content(objectMapper.writeValueAsString(credential))
                .header("Content-Type", "application/json"))
                .andExpect(status().is2xxSuccessful())
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    String body = response.getContentAsString(StandardCharsets.UTF_8);
                    Map bodyMap = objectMapper.readValue(body.getBytes(), Map.class);
                    Map<String, String> data = (HashMap<String, String>) bodyMap.get("data");
                    token = data.get("token");
                    String tokenHead = data.get("tokenHead");
                    assertEquals(TOKEN_HEAD, tokenHead);
                });
    }
}
