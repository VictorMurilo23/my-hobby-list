package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.media.type.MediaType;
import com.myhobbylistlmtd.springboot.media.type.MediaTypeRepository;

@Configuration
@ComponentScan("com.myhobbylistlmtd.springboot.migrations")
public class LoadMedia {

 /**
   * Popula o banco com todos os tipos de midias.
   * @param repository
   * @return void
   */
  @Bean("loadMediaTypes")
  public CommandLineRunner loadMediaTypes(
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

   /**
   * Popula o banco com todos os status de midias.
   * @param repository
   * @return void
   */
  @Bean("loadMediaStatus")
  public CommandLineRunner loadMediaStatus(
    final MediaStatusRepository repository
    ) {
    return args -> {
      MediaStatus[] statusArray = {
        new MediaStatus("Completo"),
        new MediaStatus("Em andamento"),
        new MediaStatus("Descontinuado")
      };
      List<MediaStatus> statusList = new ArrayList<>();
      Collections.addAll(statusList, statusArray);
      repository.saveAll(statusList);
    };
  }

  /**
   * Popula o banco com todos os status de midias.
   * @param statusRepo Repository de MediaStatus
   * @param mediaRepo Repository de Media
   * @param typeRepo Repository de MediaType
   * @return void
   */
  @Bean("runLoadMediaAndStatus")
  @DependsOn("loadMediaTypes")
  public CommandLineRunner runLoadMediaAndStatus(
    final MediaStatusRepository statusRepo,
    final MediaRepository mediaRepo,
    final MediaTypeRepository typeRepo
  ) {
    return args -> {
      // CHECKSTYLE:OFF: MagicNumber
      Media[] mediaArray = {
        new Media(
          "GTO", 208, 25,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Manga")
        ),
        new Media(
          "Final Fantasy Tactics: The War of the Lions", 42,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Jogo")
        ),
        new Media(
          "Neon Genesis Evangelion: The End of Evangelion", 1,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Filme")
        ),
        new Media(
          "Gyakkyou Burai Kaiji: Ultimate Survivor", 26,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Anime")
        ),
        new Media(
          "Breaking Bad - 1° Temporada", 26,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Série")
        ),
        new Media(
          "The Legendary Mechanic (Novel)", 1463, 15,
          statusRepo.findByStatus("Completo"), typeRepo.findByType("Livro")
        ),
      };
      List<Media> mediaList = new ArrayList<>();
      Collections.addAll(mediaList, mediaArray);

      mediaRepo.saveAll(mediaList);
    };
  }
}
