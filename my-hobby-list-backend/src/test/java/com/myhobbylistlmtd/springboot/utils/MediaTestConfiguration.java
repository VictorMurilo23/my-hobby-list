package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;

@TestConfiguration
@Profile({ "test" })
public class MediaTestConfiguration {
  @Bean
  public CommandLineRunner runMediaTestConfiguration(MediaRepository mediaRepo, MediaStatusRepository statusRepo,
      MediaTypeRepository typeRepo) {
    MediaStatus[] statusArray = {
        new MediaStatus("Completo"),
        new MediaStatus("Em andamento")
    };
    List<MediaStatus> statusList = new ArrayList<>();
    Collections.addAll(statusList, statusArray);
    statusRepo.saveAll(statusList);

    MediaType[] typesArray = {
        new MediaType("Jogo"),
        new MediaType("Manga"),
    };
    List<MediaType> typesList = new ArrayList<>();

    Collections.addAll(typesList, typesArray);

    typeRepo.saveAll(typesList);

    MediaStatus completeStatus = statusRepo.findByStatus("Completo");
    MediaStatus inProgressStatus = statusRepo.findByStatus("Em andamento");
    MediaType mangaType = typeRepo.findByType("Manga");

    List<Media> mediaList = new ArrayList<>();
    mediaList.add(
      new Media(
        "Tessst",
        208,
        completeStatus,
        mangaType,
        LocalDateTime.parse("2023-05-30T09:01:00"),
        "capa/capa",
        25
      )
    );
    mediaList.add(
      new Media(
        "tsste",
        343,
        completeStatus,
        mangaType,
        LocalDateTime.parse("2023-05-30T08:01:00"),
        "capa/capa",
        38
      )
    );
    mediaList.add(
      new Media(
        "Tes1",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-05-29T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "jjdjjwj",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-06-01T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "vbnbvbbvng",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-05-03T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "pjdpiwjpoidajhoi",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-04-20T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "lṕlpĺḱoḱojkpj",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2022-12-10T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "oiytrcvb",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-09-19T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
        "87dwdawdadw",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2023-01-03T08:01:00"),
        "capa/capa",
        3
      )
    );
    mediaList.add(
      new Media(
      "45445dwadawd",
      57,
      inProgressStatus,
      mangaType,
      LocalDateTime.parse("2023-05-10T08:05:00"),
      "capa/capa",
      3
    )
  );
    mediaList.add(
      new Media(
        "8888888899999",
        57,
        inProgressStatus,
        mangaType,
        LocalDateTime.parse("2024-05-29T08:01:00"),
        "capa/capa",
        3
      )
    );
      
    
    mediaRepo.saveAll(mediaList);
    return null;
  }
}
