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
    List<Characters> charsList = new ArrayList<Characters>();
    charsList.add(new Characters("Eikichi Onizuka", "/characters/gto/onizuka.jpg"));
    charsList.add(new Characters("Azusa Fuyutsuki", "/characters/gto/azusa-fuyutsuki.jpg"));
    charsList.add(new Characters("Hiroshi Uchiyamada", "/characters/gto/hiroshi-uchiyamada.jpg"));
    charsList.add(new Characters("Miyabi Aizawa", "/characters/gto/miyabi-aizawa.jpg"));
    charsList.add(new Characters("Ryuuji Danma", "/characters/gto/ryuuji-danma.jpg"));
    charsList.add(new Characters("Tomoko Nomura", "/characters/gto/tomoko-nomura.jpg"));
    charsList.add(new Characters("Urumi Kanzaki", "/characters/gto/urumi-kanzaki.jpg"));
    charsList.add(new Characters("Yoshito Kikuchi", "/characters/gto/yoshito-kikuchi.jpg"));

    charsList.add(new Characters("Cocoto", "/characters/cocoto-fishing-master/cocoto.png"));

    repository.saveAll(charsList);
    return null;
  }
}
