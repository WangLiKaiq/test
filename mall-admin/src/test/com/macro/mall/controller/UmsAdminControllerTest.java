package com.macro.mall.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @Author Using this class to test UmsAdminController.
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

    @DisplayName("Test for UmsAdminController's create method.")
    @Test
    public void testCreateMethod() {
    }
}
