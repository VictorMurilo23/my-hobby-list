package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.objs.MediaParams;

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
    mediaList.add(new Media(
        new MediaParams("Tessst")
            .setLength(208)
            .setVolumes(25)
            .setStatus(completeStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-30T09:01:00"))));
    mediaList.add(new Media(
        new MediaParams("tsste")
            .setLength(343)
            .setVolumes(38)
            .setStatus(completeStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-30T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("Tes1")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-29T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("jjdjjwj")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-06-01T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("vbnbvbbvng")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-03T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("pjdpiwjpoidajhoi")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-04-20T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("lṕlpĺḱoḱojkpj")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2022-12-10T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("oiytrcvb")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-09-19T08:01:00"))));
    mediaList.add(new Media(
        new MediaParams("87dwdawdadw")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-01-03T08:01:00"))));
    mediaList.add(new Media(
      new MediaParams("45445dwadawd")
          .setLength(57)
          .setVolumes(3)
          .setStatus(inProgressStatus)
          .setType(mangaType)
          .setImageUrl("capa/capa")
          .setInsertionDate(
              LocalDateTime.parse("2023-05-10T08:05:00"))));
    mediaList.add(new Media(
        new MediaParams("8888888899999")
            .setLength(57)
            .setVolumes(3)
            .setStatus(inProgressStatus)
            .setType(mangaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2024-05-29T08:01:00"))));
      
    
    mediaRepo.saveAll(mediaList);
    return null;
  }
}
