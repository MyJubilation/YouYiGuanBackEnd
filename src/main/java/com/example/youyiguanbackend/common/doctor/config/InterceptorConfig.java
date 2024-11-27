package com.example.youyiguanbackend.common.doctor.config;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description 拦截器配置
 */

import com.example.youyiguanbackend.common.doctor.common.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/doctor/login/**","/api/doctor/request-verification-code",
                        "/api/doctor/verify-code","/api/doctor/validate-face","/api/doctor/register");
    }
}
