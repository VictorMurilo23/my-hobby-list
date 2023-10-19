package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.characters.CharacterRepository;
import com.myhobbylistlmtd.springboot.characters.Characters;
import com.myhobbylistlmtd.springboot.charactersrole.CharactersRole;
import com.myhobbylistlmtd.springboot.charactersrole.CharactersRoleRepository;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharacters;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharactersRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;

@TestConfiguration
@Profile({ "test" })
public class CharactersTestConfiguration {
  @Bean
  public CommandLineRunner runLoginTestConfiguration(final CharacterRepository characterRepo,
      final MediaRepository mediaRepo,
      final CharactersRoleRepository charRoleRepo,
      final MediaCharactersRepository mediaCharRepo,
      final MediaStatusRepository statusRepo,
      final MediaTypeRepository typeRepo) {
    MediaStatus completedStatus = statusRepo.save(new MediaStatus("Completo"));
    MediaType movieType = typeRepo.save(new MediaType("Filme"));
    Media media1 = mediaRepo.save(
      new Media(
        "Teste",
        1,
        completedStatus,
        movieType,
        LocalDateTime.parse("2023-05-30T09:03:00"),
        "Teste"
      )
    );
    Media media2 = mediaRepo.save(
      new Media(
        "Teste2",
        1,
        completedStatus,
        movieType,
        LocalDateTime.parse("2023-05-30T09:04:00"),
        "Teste2"
      )
    );
    mediaRepo.save(
      new Media(
        "Teste3",
        1,
        completedStatus,
        movieType,
        LocalDateTime.parse("2023-05-30T10:03:00"),
        "Teste3"
      )
    );
    
    Characters character1 = characterRepo.save(new Characters("Personagem1", "dwahd9uwahud", "/teste1"));
    Characters character2 = characterRepo.save(new Characters("Personagem2", "diaioaw", "/teste2"));
    Characters character3 = characterRepo.save(new Characters("Personagem3", "dauguwtacfdvyuatgcfuaw", "/teste3"));
    Characters character4 = characterRepo.save(new Characters("Personagem4", "djahiowgwayfvcfawy", "/teste4"));

    CharactersRole mainRole = charRoleRepo.save(new CharactersRole("Personagem principal"));
    
    List<MediaCharacters> mediaCharacters = new ArrayList<MediaCharacters>();
    mediaCharacters.add(new MediaCharacters(media1, character1, mainRole));
    mediaCharacters.add(new MediaCharacters(media1, character2, mainRole));
    mediaCharacters.add(new MediaCharacters(media2, character3, mainRole));
    mediaCharacters.add(new MediaCharacters(media2, character4, mainRole));

    mediaCharRepo.saveAll(mediaCharacters);
    return null;
  }
}
