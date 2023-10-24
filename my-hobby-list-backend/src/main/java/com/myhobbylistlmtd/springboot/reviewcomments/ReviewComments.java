package com.myhobbylistlmtd.springboot.reviewcomments;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_comments")
public class ReviewComments {
  /**
   * Id gerado automaticamente.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "comment_id")
  @JsonView(
    {
      Views.Public.class, Views.MediaCard.class,
      Views.ReviewPage.class, Views.UserListItem.class,
      Views.Comment.class
    }
  )
  private Long commentId;

  /**
  * Chave estrageira referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonProperty("user")
  private User userId;

  /**
   * Chave estrageira referente a review.
   */
  @ManyToOne
  @PrimaryKeyJoinColumn(name = "review_id")
  @JsonProperty("review")
  private Reviews reviewId;

  /**
   * Conteúdo do comentário.
   */
  @Column(name = "commentary", nullable = false, length = 10000)
  @JsonView({ Views.Comment.class })
  private String commentary;

  /**
   * Boolean dizendo se a review foi editada ou não.
   */
  @Column(name = "edited", nullable = false)
  @JsonView({ Views.Comment.class })
  private Boolean edited = false;

  /** Data e horário de inserção da media atual.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "insertion_date", nullable = false)
  @JsonView({ Views.Comment.class })
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDateTime insertionDate = LocalDateTime.now();

  /**
   * Default constructor.
   */
  public ReviewComments() {
  }

  /**
   * Constructor com review, userId e commentary.
   * @param review Objeto de Review com a review referente ao comentário
   * @param user userId do usuário que fez o comentário
   * @param commentary conteúdo do comentário
   */
  public ReviewComments(
    final Reviews review,
    final User user,
    final String commentary
  ) {
    this.userId = user;
    this.reviewId = review;
    this.commentary = commentary;
  }

  /**
   * Getter de commentary.
   * @return Retorna o conteúdo do comentário
   */
  public String getCommentary() {
    return commentary;
  }

  /**
   * Getter de edited.
   * @return Um Boolean dizendo se o comentário foi editado ou não.
   */
  public Boolean getEdited() {
    return edited;
  }

  /**
   * Getter de commentId.
   * @return O id do comentário
   */
  public Long getCommentId() {
    return commentId;
  }

  /**
   * Setter de commentary.
   * @param commentaryStr Uma string com o comentário do usuário.
   */
  public void setCommentary(final String commentaryStr) {
    this.commentary = commentaryStr;
  }

  /**
   * Setter de edited.
   * @param editedBool Boolean dizendo se o comentário foi editado ou não
   */
  public void setEdited(final Boolean editedBool) {
    this.edited = editedBool;
  }

  /**
   * Getter de username. Usado no retorno do JSON.
   * @return Nome do usuário que fez o comentário.
   */
  @JsonView({ Views.Comment.class })
  @JsonProperty("username")
  public String getUsername() {
    return this.userId.getUsername();
  }

  /**
   * Getter de insertionDate.
   * @return A data em que o comentário foi feito
   */
  public LocalDateTime getInsertionDate() {
    return insertionDate;
  }

  /**
   * Getter de reviewId.
   * @return Um objeto com as informaçoes da review
   */
  public Reviews getReviewId() {
    return reviewId;
  }
}
