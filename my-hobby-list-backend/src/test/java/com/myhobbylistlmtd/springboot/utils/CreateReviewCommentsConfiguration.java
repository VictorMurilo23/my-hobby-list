package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsRepository;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

public class CreateReviewCommentsConfiguration {
  @Bean
  public CommandLineRunner runCreateReviewCommentsConfiguration(
    UserRepository userRepo,
    MediaRepository mediaRepo,
    MediaStatusRepository statusRepo,
    MediaTypeRepository typeRepo,
    ReviewsRepository reviewRepo
  ) {
    User user = userRepo.save(new User("Victor", "email@gmail.com", "123"));
    MediaStatus mediaStatus = statusRepo.save(new MediaStatus("Completo"));
    MediaType mediaType = typeRepo.save(new MediaType("Jogo"));
    Media media = mediaRepo.save(
      new Media(
        "Tes1",
        57,
        mediaStatus,
        mediaType,
        LocalDateTime.parse("2023-05-29T08:01:00"),
        "capa/capa",
        3
      )
    );
    reviewRepo.save(new Reviews("Teste1", true, media, user));

    return null;
  }
}
