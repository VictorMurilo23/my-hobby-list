package com.myhobbylistlmtd.springboot.reviewcomments;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_comments")
public class ReviewComments {
  /**
   * Id composto de chave estrangeiras.
   */
  @EmbeddedId
  @JsonView(Views.Public.class)
  private ReviewCommentsId id;

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
    this.id = new ReviewCommentsId(user, review);
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
   * Getter de id.
   * @return O id do comentário
   */
  public ReviewCommentsId getId() {
    return id;
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
    return this.id.getUserId().getUsername();
  }

  /**
   * Getter de insertionDate.
   * @return A data em que o comentário foi feito
   */
  public LocalDateTime getInsertionDate() {
    return insertionDate;
  }
}
