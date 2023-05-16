package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;

@Configuration
public class LoadMediaStatus {
  /**
   * Popula o banco com todos os status de midias.
   * @param repository
   * @return void
   */
  @Bean
  public CommandLineRunner runLoadMediaStatus(
    final MediaStatusRepository repository
  ) {
    return args -> {
      MediaStatus[] statusArray = {
        new MediaStatus("Completo"),
        new MediaStatus("Em andamento"),
        new MediaStatus("Descontinuado"),
      };
      List<MediaStatus> statusList = new ArrayList<>();

      Collections.addAll(statusList, statusArray);

      repository.saveAll(statusList);
    };
  }
}
