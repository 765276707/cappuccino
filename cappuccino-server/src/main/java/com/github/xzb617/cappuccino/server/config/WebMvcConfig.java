package com.github.xzb617.cappuccino.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.security.auth.AuthInterceptor;
import com.github.xzb617.cappuccino.server.security.clients.ClientsAuthInterceptor;
import com.github.xzb617.cappuccino.server.security.perms.CheckRoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private ServerProperties serverProperties;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(this.serverProperties, this.objectMapper))
                .addPathPatterns("/**")
                .excludePathPatterns("/page/**");
        registry.addInterceptor(new ClientsAuthInterceptor(serverProperties)).addPathPatterns("/**");
        registry.addInterceptor(new CheckRoleInterceptor(this.objectMapper)).addPathPatterns("/**");
    }

    /**
     * CORS过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/page/**").addResourceLocations("classpath:static/");
    }
}
