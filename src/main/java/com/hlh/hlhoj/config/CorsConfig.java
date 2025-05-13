package com.hlh.hlhoj.config;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(
                        HttpHeaders.CONTENT_DISPOSITION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.CONTENT_LENGTH
                )
                .allowCredentials(true)
                .maxAge(3600);
    }
}
