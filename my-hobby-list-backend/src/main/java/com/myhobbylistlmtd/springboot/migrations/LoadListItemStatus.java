package com.myhobbylistlmtd.springboot.migrations;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatus;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatusRepository;

@Configuration
@Profile({ "dev" })
public class LoadListItemStatus {
  /**
   * Salvar os tipos de status que itens da lista podem ter.
   * @param repository Repositório de ItemStatus
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadListItemStatus(
      final ListItemStatusRepository repository) {
    List<ListItemStatus> statusList = new ArrayList<ListItemStatus>();
    statusList.add(new ListItemStatus("Em andamento"));
    statusList.add(new ListItemStatus("Concluído"));
    statusList.add(new ListItemStatus("Droppado"));
    statusList.add(new ListItemStatus("Planejado"));
    statusList.add(new ListItemStatus("Pausado"));

    repository.saveAll(statusList);
    return null;
  }
}
