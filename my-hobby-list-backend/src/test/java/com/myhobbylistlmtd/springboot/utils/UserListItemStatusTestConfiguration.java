package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.list.status.ItemStatusRepository;
import com.myhobbylistlmtd.springboot.list.status.ItemStatus;

@TestConfiguration
@Profile({ "test" })
public class UserListItemStatusTestConfiguration {
  @Bean
  public CommandLineRunner runUserListItemStatusTestConfiguration(ItemStatusRepository repository) {
    repository.save(new ItemStatus("Em andamento"));
    repository.save(new ItemStatus("Droppado"));
    return null;
  }
}
