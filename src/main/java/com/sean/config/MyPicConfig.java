package com.sean.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//新增加一个类用来添加虚拟路径映射
@Configuration
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/*")
                .addResourceLocations("file:/Users/yinhaixiang/IdeaProjects/sean/xunwu-project/src/main/resources/static/upload/");
    }
}