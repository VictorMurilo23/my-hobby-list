package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
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
   * d.
   * @param userId d
   * @param mediaId d
   * @return d
   */
  private Reviews findReview(final Long userId, final Long mediaId) {
    Reviews review = reviewsRepo.findReviewsByUserIdAndMediaId(userId, mediaId);
    if (review == null) {
      throw new NotFoundException("Review não encontrada!");
    }
    return review;
  }

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

  /**
   * d.
   * @param body f
   * @param userId f
   */
  public void editReview(final RequestEditReview body, final Long userId) {
    Reviews review = this.findReview(userId, body.getMediaId());
    if (body.getContent() != null) {
      review.setContent(body.getContent());
    }
    if (body.getRecommended() != null) {
      review.setRecommended(body.getRecommended());
    }
    review.setEdited(true);
    reviewsRepo.save(review);
  }
}
