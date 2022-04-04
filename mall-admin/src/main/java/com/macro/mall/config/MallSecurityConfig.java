package com.macro.mall.config;

import com.macro.mall.model.UmsResource;
import com.macro.mall.security.component.*;
import com.macro.mall.security.config.IgnoreUrlsConfig;
import com.macro.mall.security.config.SecurityConfig;
import com.macro.mall.security.util.JwtTokenUtil;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.service.UmsResourceService;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private UmsResourceService resourceService;

    @Autowired
    public MallSecurityConfig(IgnoreUrlsConfig ignoreUrlsConfig, PasswordEncoder passwordEncoder, @Nullable DynamicSecurityService dynamicSecurityService,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, AuthenticationManager authenticationManager,
                          RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                          JwtTokenUtil jwtTokenUtil, @Nullable DynamicAccessDecisionManager dynamicAccessDecisionManager,
                          @Nullable DynamicSecurityFilter dynamicSecurityFilter, @Nullable DynamicSecurityMetadataSource dynamicSecurityMetadataSource,
                            UmsAdminService adminService, UmsResourceService resourceService) {
        super(ignoreUrlsConfig, passwordEncoder, dynamicSecurityService, jwtAuthenticationTokenFilter, authenticationManager,
                restfulAccessDeniedHandler, restAuthenticationEntryPoint, jwtTokenUtil, dynamicAccessDecisionManager,
                dynamicSecurityFilter, dynamicSecurityMetadataSource);
        this.adminService = adminService;
        this.resourceService = resourceService;

    }
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.listAll();
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
