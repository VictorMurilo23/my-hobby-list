package com.myhobbylistlmtd.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myhobbylistlmtd.springboot.interceptors.TokenInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
  @Autowired
  private TokenInterceptor tokenInterceptor;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins("*")
        .allowedHeaders("*");
    registry.addMapping("/list/insert")
        .allowedMethods("*")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowCredentials(true);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/list/insert");
  }
}
