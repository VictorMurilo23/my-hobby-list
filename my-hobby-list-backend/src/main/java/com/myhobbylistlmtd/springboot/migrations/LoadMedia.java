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
import com.myhobbylistlmtd.springboot.objs.MediaParams;

@Configuration
@ComponentScan("com.myhobbylistlmtd.springboot.migrations")
public class LoadMedia {

  /**
   * Popula o banco com todos os tipos de midias.
   * 
   * @param repository
   * @return void
   */
  @Bean("loadMediaTypes")
  public CommandLineRunner loadMediaTypes(
      final MediaTypeRepository repository) {
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
   * 
   * @param repository
   * @return void
   */
  @Bean("loadMediaStatus")
  public CommandLineRunner loadMediaStatus(
      final MediaStatusRepository repository) {
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
   * 
   * @param mediaRepo  Repository de Media
   * @param statusRepo Repository de MediaStatus.
   * @param typeRepo   Repository de MediaType
   * @return void
   */
  @Bean("runLoadMediaAndStatus")
  @DependsOn("loadMediaTypes")
  public CommandLineRunner runLoadMediaAndStatus(
      final MediaRepository mediaRepo,
      final MediaStatusRepository statusRepo,
      final MediaTypeRepository typeRepo) {
    return args -> {
      // CHECKSTYLE:OFF: MagicNumber
      Media[] mediaArray = {
          new Media(
              new MediaParams("GTO")
                  .setLength(208)
                  .setVolumes(25)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Manga"))
                  .setImageUrl("/gto-capa")),
          new Media(
              new MediaParams("Final Fantasy Tactics: The War of the Lions")
                  .setLength(42)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Jogo"))
                  .setImageUrl("/fft-twotl-capa")),
          new Media(
              new MediaParams("Neon Genesis Evangelion: The End of Evangelion")
                  .setLength(1)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Filme"))
                  .setImageUrl("/the-end-of-evangelion-capa")),
          new Media(
              new MediaParams("Gyakkyou Burai Kaiji: Ultimate Survivor")
                  .setLength(26)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Anime"))
                  .setImageUrl("/kaiji-1-temporada-anime-capa")),
          new Media(
              new MediaParams("Breaking Bad - 1° Temporada")
                  .setLength(7)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Série"))
                  .setImageUrl("/breaking-bad-1-temporada-capa")),
          new Media(
              new MediaParams("The Legendary Mechanic (Novel)")
                  .setLength(1463)
                  .setVolumes(15)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Livro"))
                  .setImageUrl("/novel-the-legendary-mechanic-capa"))
      };
      List<Media> mediaList = new ArrayList<>();
      Collections.addAll(mediaList, mediaArray);

      mediaRepo.saveAll(mediaList);
    };
  }
}
