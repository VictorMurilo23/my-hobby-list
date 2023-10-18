package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewComments;
import com.myhobbylistlmtd.springboot.utils.Views;

public class ResponseReviewComments {
  /**
   * Lista com todos os comentários da review.
   */
  @JsonView({ Views.Comment.class })
  private List<ReviewComments> comments;

  /**
   * d.
   * @param comments
   */
  public ResponseReviewComments(final List<ReviewComments> comments) {
    this.comments = comments;
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
}
