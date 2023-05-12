package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.myhobbylistlmtd.springboot.User.User;
import com.myhobbylistlmtd.springboot.User.UserRepository;

@TestConfiguration
public class LoginTestConfiguration {

  @Bean
  CommandLineRunner initDatabase(UserRepository repository) {
    return args -> {
      repository.save(new User("Victor", "email@gmail.com", "1456"));
    };
  }
}
