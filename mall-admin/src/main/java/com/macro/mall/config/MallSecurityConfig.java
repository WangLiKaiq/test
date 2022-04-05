package com.macro.mall.config;

import com.macro.mall.security.component.RestAuthenticationEntryPoint;
import com.macro.mall.security.component.RestfulAccessDeniedHandler;
import com.macro.mall.security.config.IgnoreUrlsConfig;
import com.macro.mall.security.config.SecurityConfig;
import com.macro.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * mall-security模块相关配置
 *
 * @author Komorebi
 * @date 2022-04-04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {

    private UmsAdminService adminService;

    @Autowired
    public MallSecurityConfig(IgnoreUrlsConfig ignoreUrlsConfig, PasswordEncoder passwordEncoder,
                              RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                              UmsAdminService adminService) {
        super(ignoreUrlsConfig, passwordEncoder,
                restfulAccessDeniedHandler, restAuthenticationEntryPoint);
        this.adminService = adminService;

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

}
