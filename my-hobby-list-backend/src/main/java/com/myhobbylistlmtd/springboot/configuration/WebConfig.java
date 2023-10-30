package com.myhobbylistlmtd.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/list/insert");
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/list/edit");
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/user/profile/change-profile-image");
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/review-comments/create");
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/review-comments/edit");
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/reviews/**").excludePathPatterns("/reviews/find/**")
    .excludePathPatterns("/reviews/find-all*/**");
  }
}
