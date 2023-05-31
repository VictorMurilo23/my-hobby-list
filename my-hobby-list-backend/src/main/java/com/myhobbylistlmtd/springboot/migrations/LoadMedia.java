package com.myhobbylistlmtd.springboot.migrations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.media.type.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.objs.MediaParams;

@Configuration
@ComponentScan("com.myhobbylistlmtd.springboot.migrations")
@Profile({ "production" })
public class LoadMedia {
  /**
   * Popula o banco com todos os status de midias.
   * @param mediaRepo  Repository de Media
   * @param statusRepo Repository de MediaStatus.
   * @param typeRepo   Repository de MediaType
   * @return void
   */
  @Bean
  @DependsOn("runLoadMediaTypes")
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
                  .setImageUrl("images/covers/gto-manga-capa.jpg")
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:01:00")
                  )),
          new Media(
              new MediaParams("Final Fantasy Tactics: The War of the Lions")
                  .setLength(42)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Jogo"))
                  .setImageUrl("images/covers/fft-twotl-capa.jpg")
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:02:00")
                  )),
          new Media(
              new MediaParams("Neon Genesis Evangelion: The End of Evangelion")
                  .setLength(1)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Filme"))
                  .setImageUrl("images/covers/the-end-of-evangelion-capa.jpg")
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:03:00")
                  )),
          new Media(
              new MediaParams("Gyakkyou Burai Kaiji: Ultimate Survivor")
                  .setLength(26)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Anime"))
                  .setImageUrl(
                    "images/covers/kaiji-1-temporada-anime-capa.jpg"
                  )
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:04:00")
                  )),
          new Media(
              new MediaParams("Breaking Bad - 1° Temporada")
                  .setLength(7)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Série"))
                  .setImageUrl(
                    "images/covers/breaking-bad-1-temporada-capa.jpg"
                  )
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:05:00")
                  )),
          new Media(
              new MediaParams("The Legendary Mechanic (Novel)")
                  .setLength(1463)
                  .setVolumes(15)
                  .setStatus(statusRepo.findByStatus("Completo"))
                  .setType(typeRepo.findByType("Livro"))
                  .setImageUrl(
                    "images/covers/novel-the-legendary-mechanic-capa.jpg"
                  )
                  .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:06:00")
                  ))
      };
      List<Media> mediaList = new ArrayList<>();
      Collections.addAll(mediaList, mediaArray);

      mediaRepo.saveAll(mediaList);
    };
  }
}
