package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
public class LoginTestConfiguration {

  @Bean
  public CommandLineRunner runLoginTestConfiguration(UserRepository repository) {
    repository.save(new User("Victor", "email@gmail.com", "1456"));
    return null;
  }
}
