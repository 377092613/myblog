package com.myblog.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //  获取与jar同级目录下的upload文件夹  设置与jar同级静态资源配置
        // 本地获取的路径 D:\idea\springboot2.x\target  upload 跟 项目jar平级
        String path = null;
        String os=System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            path="F://img//upload/";
        }else if(os.toLowerCase().contains("linux")){
            path="/SpringBoot/img/upload/";
        }
            registry.addResourceHandler("/img/upload/**").addResourceLocations("file:" + path);
        }
}
