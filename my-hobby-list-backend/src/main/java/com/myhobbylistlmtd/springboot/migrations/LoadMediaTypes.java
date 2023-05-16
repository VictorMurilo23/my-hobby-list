package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myhobbylistlmtd.springboot.media.type.MediaType;
import com.myhobbylistlmtd.springboot.media.type.MediaTypeRepository;

@Configuration
public class LoadMediaTypes {
  /**
   * Popula o banco com todos os tipos de midias.
   * @param repository
   * @return void
   */
  @Bean
  public CommandLineRunner runLoadMediaTypes(
    final MediaTypeRepository repository
    ) {
    return args -> {
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
    };
  }
}
