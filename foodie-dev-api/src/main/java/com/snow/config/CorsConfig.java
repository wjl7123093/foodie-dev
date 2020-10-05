package com.snow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Desc: 跨域访问配置
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }


    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加 cors 配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://192.168.197.128:8080");

        // 设置是否发送 cookie 信息
        config.setAllowCredentials(true);

        // 设置允许请求的方式
        config.addAllowedMethod("*");

        // 设置允许的 header
        config.addAllowedHeader("*");

        // 2. 为 url 添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        // 3. 返回重新定义好的 corsSource
        return new CorsFilter(corsSource);

    }

}
