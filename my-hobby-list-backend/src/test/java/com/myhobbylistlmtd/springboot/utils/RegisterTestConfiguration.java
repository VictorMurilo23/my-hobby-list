package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
@Profile({ "test" })
public class RegisterTestConfiguration {
  @Bean
  public CommandLineRunner runRegisterTestConfiguration(UserRepository repository) {
    repository.save(new User("Teste1", "email@teste.com", "1456"));
    return null;
  }
}
