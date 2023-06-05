package com.myhobbylistlmtd.springboot.migrations;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.list.status.ItemStatus;
import com.myhobbylistlmtd.springboot.list.status.ItemStatusRepository;

@Configuration
@Profile({ "production" })
public class LoadListItemStatus {
  /**
   * Salvar os tipos de status que itens da lista podem ter.
   * @param repository Repositório de ItemStatus
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadListItemStatus(
      final ItemStatusRepository repository) {
    List<ItemStatus> statusList = new ArrayList<ItemStatus>();
    statusList.add(new ItemStatus("Em andamento"));
    statusList.add(new ItemStatus("Concluído"));
    statusList.add(new ItemStatus("Droppado"));
    statusList.add(new ItemStatus("Planejado"));
    statusList.add(new ItemStatus("Pausado"));

    repository.saveAll(statusList);
    return null;
  }
}
