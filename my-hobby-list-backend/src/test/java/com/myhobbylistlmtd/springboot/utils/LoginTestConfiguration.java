package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;

import com.myhobbylistlmtd.springboot.abc.AbstractMigration;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
public class LoginTestConfiguration extends AbstractMigration<UserRepository> {

  @Override
  public CommandLineRunner initDatabase(UserRepository repository) {
    repository.save(new User("Victor", "email@gmail.com", "1456"));
    return null;
  }
}
