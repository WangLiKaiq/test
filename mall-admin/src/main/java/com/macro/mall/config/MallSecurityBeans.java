package com.macro.mall.config;

import com.macro.mall.model.UmsResource;
import com.macro.mall.security.component.DynamicSecurityService;
import com.macro.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author komorebi
 * @since 2022-04-05
 * Using this Configuration to generate beans that pertain to security.
 */
@Configuration
public class MallSecurityBeans {
    private UmsResourceService resourceService;

    @Autowired
    public MallSecurityBeans(UmsResourceService umsResourceService) {
        this.resourceService = umsResourceService;
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>(10);
            List<UmsResource> resourceList = resourceService.listAll();
            for (UmsResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }
}
