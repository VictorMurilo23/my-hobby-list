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
    statusList.add(new Characters("Eikichi Onizuka", "/characters/gto/onizuka.jpg"));
    statusList.add(new Characters("Azusa Fuyutsuki", "/characters/gto/azusa-fuyutsuki.jpg"));
    statusList.add(new Characters("Hiroshi Uchiyamada", "/characters/gto/hiroshi-uchiyamada.jpg"));
    statusList.add(new Characters("Miyabi Aizawa", "/characters/gto/miyabi-aizawa.jpg"));
    statusList.add(new Characters("Ryuuji Danma", "/characters/gto/ryuuji-danma.jpg"));
    statusList.add(new Characters("Tomoko Nomura", "/characters/gto/tomoko-nomura.jpg"));
    statusList.add(new Characters("Urumi Kanzaki", "/characters/gto/urumi-kanzaki.jpg"));
    statusList.add(new Characters("Yoshito Kikuchi", "/characters/gto/yoshito-kikuchi.jpg"));
    repository.saveAll(statusList);
    return null;
  }
}
