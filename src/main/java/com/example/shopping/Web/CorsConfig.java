package com.example.shopping.Web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**") // Allow all endpoints
        .allowedOrigins("http://localhost:3000") // Replace with your frontend URL
        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
        .allowedHeaders("*") // Allow all headers
        .allowCredentials(true); // Allow cookies if 
    }
}



