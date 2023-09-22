package com.myhobbylistlmtd.springboot.migrations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.objs.MediaParams;

@Configuration
@ComponentScan("com.myhobbylistlmtd.springboot.migrations")
@Profile({ "production" })
public class LoadMedia {
  /**
   * Repositório de media.
   */
  @Autowired
  private MediaRepository mediaRepo;

  /**
   * Repositório de MediaStatus.
   */
  @Autowired
  private MediaStatusRepository statusRepo;

  /**
   * Repositório de MediaType.
   */
  @Autowired
  private MediaTypeRepository typeRepo;

  /**
   * Salva todas as medias.
   */
  public final void init() {
    MediaStatus completedStatus = statusRepo.findByStatus("Completo");
    MediaType gameType = typeRepo.findByType("Jogo");
    // CHECKSTYLE:OFF: MagicNumber
    Media[] mediaArray = {
        new Media(
            new MediaParams("GTO")
                .setLength(208)
                .setVolumes(25)
                .setStatus(completedStatus)
                .setType(typeRepo.findByType("Manga"))
                .setImageUrl("images/covers/gto-manga-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:01:00"))),
        new Media(
            new MediaParams("Final Fantasy Tactics: The War of the Lions")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl("images/covers/fft-twotl-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:02:00"))),
        new Media(
            new MediaParams("Neon Genesis Evangelion: The End of Evangelion")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(typeRepo.findByType("Filme"))
                .setImageUrl("images/covers/the-end-of-evangelion-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:03:00"))),
        new Media(
            new MediaParams("Gyakkyou Burai Kaiji: Ultimate Survivor")
                .setLength(26)
                .setStatus(completedStatus)
                .setType(typeRepo.findByType("Anime"))
                .setImageUrl(
                    "images/covers/kaiji-1-temporada-anime-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:04:00"))),
        new Media(
            new MediaParams("Breaking Bad - 1° Temporada")
                .setLength(7)
                .setStatus(completedStatus)
                .setType(typeRepo.findByType("Série"))
                .setImageUrl(
                    "images/covers/breaking-bad-1-temporada-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:05:00"))),
        new Media(
            new MediaParams("The Legendary Mechanic (Novel)")
                .setLength(1463)
                .setVolumes(15)
                .setStatus(completedStatus)
                .setType(typeRepo.findByType("Livro"))
                .setImageUrl(
                    "images/covers/novel-the-legendary-mechanic-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-05-30T09:06:00"))),
        new Media(
            new MediaParams("Cocoto Fishing Master")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl("images/covers/cocoto-fishing-master-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:01:00"))),
        new Media(
            new MediaParams("Samurai Jack: The Shadow of Aku")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl(
                    "images/covers/samurai-jack-shadow-of-aku-capa.jpg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:02:00"))),
        new Media(
            new MediaParams("God of War")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl(
                    "images/covers/god-of-war-capa.png")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:03:00"))),
        new Media(
            new MediaParams("God of War 2")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl(
                    "images/covers/god-of-war-2-capa.png")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:04:00"))),
        new Media(
            new MediaParams("Nioh")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl(
                    "images/covers/nioh-capa.webp")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:05:00"))),
        new Media(
            new MediaParams("Bloodborne")
                .setLength(1)
                .setStatus(completedStatus)
                .setType(gameType)
                .setImageUrl(
                    "images/covers/bloodborne-capa.jpeg")
                .setInsertionDate(
                    LocalDateTime.parse("2023-07-11T14:06:00"))),
    };
    List<Media> mediaList = new ArrayList<>();
    Collections.addAll(mediaList, mediaArray);

    mediaRepo.saveAll(mediaList);
  }

}
