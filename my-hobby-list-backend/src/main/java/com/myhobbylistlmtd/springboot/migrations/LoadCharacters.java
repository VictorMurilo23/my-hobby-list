package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.characters.CharacterRepository;
import com.myhobbylistlmtd.springboot.characters.Characters;

@Configuration
@Profile({ "production" })
public class LoadCharacters {
  /**
   * Salvar os tipos de status que itens da lista podem ter.
   * @param repository Repositório de ItemStatus
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadCharacters(
      final CharacterRepository repository) {
    List<Characters> statusList = new ArrayList<Characters>();
    statusList.add(new Characters("Eikichi Onizuka", "Professor", "/characters/gto/onizuka.jpg"));
    repository.saveAll(statusList);
    return null;
  }
}
