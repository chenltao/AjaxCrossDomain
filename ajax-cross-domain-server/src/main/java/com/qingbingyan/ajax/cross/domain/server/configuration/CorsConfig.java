package com.qingbingyan.ajax.cross.domain.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bear
 * @Date 2020/9/9
 */
//@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许的域
                .allowedOrigins("*")
                //允许的请求方法
                .allowedMethods("*")
                //允许的请求头
                .allowedHeaders("*")
                //允许携带 cookie
                .allowCredentials(true)
                //设置预检请求缓存
                .maxAge(3600)
                ;
    }

}
