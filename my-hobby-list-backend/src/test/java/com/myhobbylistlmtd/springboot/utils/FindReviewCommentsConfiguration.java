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
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewComments;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewCommentsRepository;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsRepository;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
@Profile({ "test" })
public class FindReviewCommentsConfiguration {
  @Bean
  public CommandLineRunner runFindReviewCommentsConfiguration(UserRepository userRepo, MediaRepository mediaRepo,
      MediaStatusRepository statusRepo,
      MediaTypeRepository typeRepo,
      ReviewsRepository reviewRepo,
      ReviewCommentsRepository reviewCommentsRepo) {
    User user = userRepo.save(
      new User("Victor", "emaifl@gmail.com", "123")
    );
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
    Reviews re = reviewRepo.save(new Reviews("Teste", true, media, user));

    
    reviewCommentsRepo.save(new ReviewComments(re, user, "Muito bom"));
    reviewCommentsRepo.save(new ReviewComments(re, userRepo.save(new User("Victo", "emaildwadwwa@gmail.com", "123")), "Muito bom"));
    return null;
  }
}
