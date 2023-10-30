package com.myhobbylistlmtd.springboot.migrations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;

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
        "GTO",
        208,
        completedStatus,
        typeRepo.findByType("Manga"),
        LocalDateTime.parse("2023-05-30T09:01:00"),
        "images/covers/gto-manga-capa.jpg",
        25
      ),
      new Media(
        "Final Fantasy Tactics: The War of the Lions",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-05-30T09:02:00"),
        "images/covers/fft-twotl-capa.jpg"
      ),
      new Media(
        "Neon Genesis Evangelion: The End of Evangelion",
        1,
        completedStatus,
        typeRepo.findByType("Filme"),
        LocalDateTime.parse("2023-05-30T09:03:00"),
        "images/covers/the-end-of-evangelion-capa.jpg"
      ),
      new Media(
        "Gyakkyou Burai Kaiji: Ultimate Survivor",
        26,
        completedStatus,
        typeRepo.findByType("Anime"),
        LocalDateTime.parse("2023-05-30T09:04:00"),
        "images/covers/kaiji-1-temporada-anime-capa.jpg"
      ),
      new Media(
        "Breaking Bad - 1° Temporada",
        7,
        completedStatus,
        typeRepo.findByType("Série"),
        LocalDateTime.parse("2023-05-30T09:05:00"),
        "images/covers/breaking-bad-1-temporada-capa.jpg"
      ),
      new Media(
        "The Legendary Mechanic (Novel)",
        1463,
        completedStatus,
        typeRepo.findByType("Livro"),
        LocalDateTime.parse("2023-05-30T09:06:00"),
        "images/covers/novel-the-legendary-mechanic-capa.jpg",
        15
      ),
      new Media(
        "Cocoto Fishing Master",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:01:00"),
        "images/covers/cocoto-fishing-master-capa.jpg"
      ),
      new Media(
        "Samurai Jack: The Shadow of Aku",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:02:00"),
        "images/covers/samurai-jack-shadow-of-aku-capa.jpg"
      ),
      new Media(
        "God of War",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:03:00"),
        "images/covers/god-of-war-capa.png"
      ),
      new Media(
        "God of War 2",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:04:00"),
        "images/covers/god-of-war-2-capa.png"
      ),
      new Media(
        "Nioh",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:05:00"),
        "images/covers/nioh-capa.webp"
      ),
      new Media(
        "Bloodborne",
        1,
        completedStatus,
        gameType,
        LocalDateTime.parse("2023-07-11T14:06:00"),
        "images/covers/bloodborne-capa.jpeg"
      )
    };
    List<Media> mediaList = new ArrayList<>();
    Collections.addAll(mediaList, mediaArray);

    mediaRepo.saveAll(mediaList);
  }

}
