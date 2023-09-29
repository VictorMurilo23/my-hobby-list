package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;

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
import com.myhobbylistlmtd.springboot.objs.MediaParams;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsRepository;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
@Profile({ "test" })
public class FindReviewsConfiguration {
  @Bean
  public CommandLineRunner runEditReviewsConfiguration(UserRepository userRepo, MediaRepository mediaRepo,
      MediaStatusRepository statusRepo,
      MediaTypeRepository typeRepo,
      ReviewsRepository reviewRepo) {
    MediaStatus mediaStatus = statusRepo.save(new MediaStatus("Completo"));
    MediaType mediaType = typeRepo.save(new MediaType("Jogo"));
    Media media = mediaRepo.save(new Media(
        new MediaParams("Tes1")
            .setLength(57)
            .setVolumes(3)
            .setStatus(mediaStatus)
            .setType(mediaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-29T08:01:00"))));
    for (int index = 1; index <= 16; index += 1) {
      User user = userRepo.save(
        new User("Victor" + index, "email" + index + "@gmail.com", "123")
      );
      reviewRepo.save(new Reviews("Teste" + index, true, media, user));
    }
    return null;
  }
}
