package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;

@Configuration
@Profile({ "dev" })
public class LoadMediaTypes {
  /**
   * Popula o banco com todos os tipos de midias.
   * @param repository
   * @return void
   */
  @Bean
  public CommandLineRunner runLoadMediaTypes(
      final MediaTypeRepository repository) {
    MediaType[] typesArray = {
        new MediaType("Jogo"),
        new MediaType("Manga"),
        new MediaType("Livro"),
        new MediaType("Anime"),
        new MediaType("Filme"),
        new MediaType("Série")
    };
    List<MediaType> typesList = new ArrayList<>();

    Collections.addAll(typesList, typesArray);

    repository.saveAll(typesList);
    return null;
  }
}
