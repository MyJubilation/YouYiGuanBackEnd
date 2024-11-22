package com.example.youyiguanbackend.common.doctor.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//允许跨域的相关配置
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许那些请求源跨域访问，比如:http://localhost8080,如果允许所有请求源跨域访问，使用"*".
        config.addAllowedOriginPattern("*");
        //允许所有的请求头跨域访问
        config.addAllowedHeader("*");
        //允许所有的请求方式跨域访问，如：post，get等
        config.addAllowedMethod("*");
        //允许前端请求携带认证信息，如果前端打开了AllowCredentials设置，则后台服务也必须打开该服务.
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}