package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@Service
public class ReviewsService {
  /**
   * Repositório de reviews.
   */
  @Autowired
  private ReviewsRepository reviewsRepo;

  /**
   * userService.
   */
  @Autowired
  private UserService userService;

  /**
   * mediaService.
   */
  @Autowired
  private MediaService mediaService;

  /**
   * bla.
   * @param body
   * @param userId
   */
  public void createReview(final RequestCreateReview body, final Long userId) {
    User user = userService.findById(userId);
    Media media = mediaService.findById(body.getMediaId());
    Reviews review = new Reviews(
      body.getContent(), body.getRecommended(), media, user
    );
    reviewsRepo.save(review);
  }
}
