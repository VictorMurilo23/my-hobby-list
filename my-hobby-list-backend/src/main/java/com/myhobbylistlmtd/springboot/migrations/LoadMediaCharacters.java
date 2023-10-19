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
      CharactersRole secundary = charRoleRepo.findByRoleName("Personagem secundário");
      Media gto = mediaRepo.findByName("GTO");
      Media evangelion = mediaRepo.findByName("Neon Genesis Evangelion: The End of Evangelion");
      Media samuraiJack = mediaRepo.findByName("Samurai Jack: The Shadow of Aku");
      Media finalFantasyTactics = mediaRepo.findByName("Final Fantasy Tactics: The War of the Lions");
      Media legendaryMechanic = mediaRepo.findByName("The Legendary Mechanic (Novel)");
      Media bloodborne = mediaRepo.findByName("Bloodborne");
      Media breakingBad = mediaRepo.findByName("Breaking Bad - 1° Temporada");

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

      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Shinji Ikari"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Gendou Ikari"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Kaworu Nagisa"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Misato Katsuragi"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Rei Ayanami"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Ritsuko Akagi"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Ryouji Kaji"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          evangelion,
          characterRepo.findByName("Asuka Langley Souryuu"),
          secundary
        )
      );

      charsList.add(
        new MediaCharacters(
          samuraiJack,
          characterRepo.findByName("Aku"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          samuraiJack,
          characterRepo.findByName("Jack"),
          main
        )
      );

      charsList.add(
        new MediaCharacters(
          finalFantasyTactics,
          characterRepo.findByName("Delita Heiral"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          finalFantasyTactics,
          characterRepo.findByName("Ramza Beoulve"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          finalFantasyTactics,
          characterRepo.findByName("Alma Beoulve"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          finalFantasyTactics,
          characterRepo.findByName("Ovelia Atkascha"),
          secundary
        )
      );

      charsList.add(
        new MediaCharacters(
          legendaryMechanic,
          characterRepo.findByName("Hila"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          legendaryMechanic,
          characterRepo.findByName("Esper God"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          legendaryMechanic,
          characterRepo.findByName("Han Xiao"),
          main
        )
      );

      charsList.add(
        new MediaCharacters(
          bloodborne,
          characterRepo.findByName("O Caçador"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          bloodborne,
          characterRepo.findByName("Alfred"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          bloodborne,
          characterRepo.findByName("Boneca"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          bloodborne,
          characterRepo.findByName("Eileen O Corvo"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          bloodborne,
          characterRepo.findByName("Gehrman"),
          secundary
        )
      );

      charsList.add(
        new MediaCharacters(
          breakingBad,
          characterRepo.findByName("Walter White"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          breakingBad,
          characterRepo.findByName("Jesse Pinkman"),
          main
        )
      );
      charsList.add(
        new MediaCharacters(
          breakingBad,
          characterRepo.findByName("Marie Schrader"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          breakingBad,
          characterRepo.findByName("Hank Schrader"),
          secundary
        )
      );
      charsList.add(
        new MediaCharacters(
          breakingBad,
          characterRepo.findByName("Skylar White"),
          secundary
        )
      );

      mediaCharRepo.saveAll(charsList);
    };
  }
}
