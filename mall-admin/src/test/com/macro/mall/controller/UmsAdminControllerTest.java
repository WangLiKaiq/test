package com.macro.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.common.api.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @DisplayName("Test login method. Then gets token.")
    @Test
    public void loginSuccess() throws Exception {
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
                    CommonResult commonResult = objectMapper.readValue(body.getBytes(), CommonResult.class);
                    long code = commonResult.getCode();
                    String message = commonResult.getMessage();
                    Map<String, String> data = (HashMap<String, String>) commonResult.getData();
                    String token = data.get("token");
                    String tokenHead = data.get("tokenHead");
                    assertEquals("操作成功", message, "The message is Wrong!");
                    assertEquals(200, code, "The code is wrong!");
                    assertEquals(TOKEN_HEAD, tokenHead, "TokenHead is wrong!");
                    assertTrue(StringUtils.isNotBlank(token), "The token is illegal!");
                });
    }

    @DisplayName("")
    @Test
    public void loginFail() throws Exception {
        Map<String, String> credential = new HashMap<String, String>();
        credential.put("username", USERNAME);
        credential.put("password", "failPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/admin/login")
                .content(objectMapper.writeValueAsString(credential))
                .header("Content-Type", "application/json"))
                .andExpect(status().is4xxClientError())
                .andDo(result -> {
                    String body = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    CommonResult commonResult = objectMapper.readValue(body.getBytes(), CommonResult.class);
                    String message = (String) commonResult.getMessage();
                    assertEquals("用户名或密码错误", message, "The The credential error message is wrong!");
                    assertEquals(401, commonResult.getCode());
                });
    }
}
