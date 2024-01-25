package org.example.han.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    @Value("${board.file.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/Users/user/Documents/dev/simple-board/board/upload-file/**")
//                .addResourceLocations("file:///" + "/Users/user/Documents/dev/simple-board/board/upload-file/");
        File currentDir = new File("");
        registry.addResourceHandler(currentDir.getAbsolutePath()+"/upload-file/**")
                .addResourceLocations("file:///" + currentDir.getAbsolutePath()+"/upload-file/");
    }
}