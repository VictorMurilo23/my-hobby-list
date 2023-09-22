package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;

@Configuration
@Profile({ "production" })
public class LoadMediaStatus {
  /**
   * Popula o banco com todos os status de midias.
   * @param repository
   * @return void
   */
  @Bean
  public CommandLineRunner runLoadMediaStatus(
      final MediaStatusRepository repository) {
    MediaStatus[] statusArray = {
        new MediaStatus("Completo"),
        new MediaStatus("Em andamento"),
        new MediaStatus("Descontinuado")
    };
    List<MediaStatus> statusList = new ArrayList<>();
    Collections.addAll(statusList, statusArray);
    repository.saveAll(statusList);
    return null;
  }
}
