package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.media.type.MediaType;
import com.myhobbylistlmtd.springboot.media.type.MediaTypeRepository;
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
    User user = userRepo.save(new User("Victor", "email@gmail.com", "123"));
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
    reviewRepo.save(new Reviews("Teste1", true, media, user));
    reviewRepo.save(new Reviews("Teste2", true, media, user));
    reviewRepo.save(new Reviews("Teste3", true, media, user));
    reviewRepo.save(new Reviews("Teste4", true, media, user));
    reviewRepo.save(new Reviews("Teste5", true, media, user));
    reviewRepo.save(new Reviews("Teste6", true, media, user));
    reviewRepo.save(new Reviews("Teste7", true, media, user));
    reviewRepo.save(new Reviews("Teste8", true, media, user));
    reviewRepo.save(new Reviews("Teste9", true, media, user));
    reviewRepo.save(new Reviews("Teste10", true, media, user));
    reviewRepo.save(new Reviews("Teste11", true, media, user));
    reviewRepo.save(new Reviews("Teste12", true, media, user));
    reviewRepo.save(new Reviews("Teste13", true, media, user));
    reviewRepo.save(new Reviews("Teste14", true, media, user));
    reviewRepo.save(new Reviews("Teste15", true, media, user));
    reviewRepo.save(new Reviews("Teste16", true, media, user));


    return null;
  }
}