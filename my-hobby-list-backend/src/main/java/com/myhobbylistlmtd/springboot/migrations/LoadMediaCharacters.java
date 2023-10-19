package com.myhobbylistlmtd.springboot.migrations;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.characters.CharacterRepository;
import com.myhobbylistlmtd.springboot.charactersrole.CharactersRole;
import com.myhobbylistlmtd.springboot.charactersrole.CharactersRoleRepository;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharacters;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharactersRepository;

@Configuration
@ComponentScan("com.myhobbylistlmtd.springboot.migrations")
@Profile({ "production" })
public class LoadMediaCharacters {

  /**
   * Dados da tabela MediaCharacters.
   * @param ctx Contexto da aplicação
   * @param mediaRepo Repository de media
   * @param charRoleRepo Repository de CharacterRole
   * @param characterRepo Repository de Characters
   * @param mediaCharRepo Repository de MediaCharacters
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadMediaCharacters(
      final ApplicationContext ctx,
      final MediaRepository mediaRepo,
      final CharactersRoleRepository charRoleRepo,
      final CharacterRepository characterRepo,
      final MediaCharactersRepository mediaCharRepo) {
    return args -> {
      ctx.getBean(LoadMedia.class).init();

      CharactersRole main = charRoleRepo.findByRoleName("Personagem principal");
      Media gto = mediaRepo.findByName("GTO");

      List<MediaCharacters> charsList = new LinkedList<MediaCharacters>();
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Eikichi Onizuka"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Azusa Fuyutsuki"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Hiroshi Uchiyamada"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Miyabi Aizawa"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Ryuuji Danma"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Tomoko Nomura"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Urumi Kanzaki"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          gto,
          characterRepo.findByName("Yoshito Kikuchi"),
          main
        )
      );

      charsList.add(
        new MediaCharacters(
          mediaRepo.findByName("Cocoto Fishing Master"),
          characterRepo.findByName("Cocoto"),
          main
        )
      );

      mediaCharRepo.saveAll(charsList);
    };
  }
}
