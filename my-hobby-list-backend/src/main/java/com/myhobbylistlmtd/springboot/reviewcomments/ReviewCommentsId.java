package com.myhobbylistlmtd.springboot.reviewcomments;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.user.User;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Embeddable
public class ReviewCommentsId {
  /**
  * Chave referente a review.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "review_id")
  @JsonProperty("review")
  private Reviews reviewId;

  /**
  * Chave referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonProperty("user")
  private User userId;

  /**
  * Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public ReviewCommentsId() {
  }

  /**
  * Default constructor.
  * @param userId Id referente ao usuário.
  * @param reviewId Id referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public ReviewCommentsId(final User userId, final Reviews reviewId) {
    this.userId = userId;
    this.reviewId = reviewId;
  }

  /**
   * Getter do atributo reviewId.
   * @return Um objeto de Reviews.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Reviews getReviewId() {
    return reviewId;
  }
    /**
   * Getter do atributo user.
   * @return Um objeto de User
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public User getUserId() {
    return userId;
  }

  /**
   * Cria uma hash do objeto atual.
   * @return Uma hash.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public int hashCode() {
    return Objects.hash(userId, reviewId);
  }

    /**
   * Compara com outro objeto de ReviewsId.
   * @return Um boolean dizendo se os objetos são iguais ou não
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ReviewCommentsId other = (ReviewCommentsId) obj;
    return Objects.equals(userId, other.userId)
    && Objects.equals(reviewId, other.reviewId);
  }
}
