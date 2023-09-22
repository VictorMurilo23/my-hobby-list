package com.myhobbylistlmtd.springboot.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatus;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatusRepository;

@TestConfiguration
@Profile({ "test" })
public class UserListItemStatusTestConfiguration {
  @Bean
  public CommandLineRunner runUserListItemStatusTestConfiguration(ListItemStatusRepository repository) {
    repository.save(new ListItemStatus("Em andamento"));
    repository.save(new ListItemStatus("Droppado"));
    return null;
  }
}
