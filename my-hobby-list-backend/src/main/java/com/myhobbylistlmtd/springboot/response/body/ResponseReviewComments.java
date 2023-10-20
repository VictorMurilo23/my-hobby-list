package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewComments;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.utils.Views;

public class ResponseReviewComments {
  /**
   * Lista com todos os comentários da review.
   */
  @JsonView({ Views.Comment.class })
  private List<ReviewComments> comments;

  /**
   * Review.
   */
  @JsonView({ Views.Review.class })
  private Reviews review;

  /**
   * d.
   * @param comments
   * @param review
   */
  public ResponseReviewComments(
    final List<ReviewComments> comments, final Reviews review
  ) {
    this.comments = comments;
    this.review = review;
  }

  /**
   * Getter de comments.
   * @return Uma lista contendo todos os comentários da review
   */
  public List<ReviewComments> getComments() {
    return comments;
  }

  /**
   * Setter de comments.
   * @param commentsList Lista contendo comentários da review
   */
  public void setComments(final List<ReviewComments> commentsList) {
    this.comments = commentsList;
  }

  /**
   * Getter de review.
   * @return Um objeto de review
   */
  public Reviews getReview() {
    return review;
  }

  /**
   * Setter de review.
   * @param reviewObj Objeto de review
   */
  public void setReview(final Reviews reviewObj) {
    this.review = reviewObj;
  }
}
