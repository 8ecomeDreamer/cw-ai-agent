package com.example.CWAIAgent.demos.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    /**
     * 配置跨域访问规则
     * 该方法定义了哪些跨域请求可以被允许
     */
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求路径
        registry.addMapping("/**")
                // 允许携带认证信息（如Cookie）
                .allowCredentials(true)
                // 使用patterns代替origins以支持通配符（解决与allowCredentials的冲突）
                .allowedOriginPatterns("*")
                // 允许的HTTP方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许所有请求头
                .allowedHeaders("*")
                // 允许所有响应头暴露给前端
                .exposedHeaders("*");
    }
}
