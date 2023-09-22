package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.charactersrole.CharactersRole;
import com.myhobbylistlmtd.springboot.charactersrole.CharactersRoleRepository;

@Configuration
@Profile({ "production" })
public class LoadCharacterRoles {
  /**
   * Salvar os tipos de status que itens da lista podem ter.
   * @param repository Repositório de ItemStatus
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadCharacterRoles(
      final CharactersRoleRepository repository) {
    List<CharactersRole> statusList = new ArrayList<CharactersRole>();
    statusList.add(new CharactersRole("Personagem principal"));
    statusList.add(new CharactersRole("Personagem secundário"));
    statusList.add(new CharactersRole("Antagonista"));

    repository.saveAll(statusList);
    return null;
  }
}
