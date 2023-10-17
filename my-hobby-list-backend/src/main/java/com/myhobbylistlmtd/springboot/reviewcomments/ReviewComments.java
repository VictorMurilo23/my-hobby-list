package com.myhobbylistlmtd.springboot.reviewcomments;

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
  private String commentary;

  /**
   * Boolean dizendo se a review foi editada ou não.
   */
  @Column(name = "edited", nullable = false)
  private Boolean edited = false;

  /**
   * Default constructor.
   */
  public ReviewComments() {
  }

  /**
   * Constructor com review, userId e commentary.
   * @param review Objeto de Review com a review referente ao comentário
   * @param userId userId do usuário que fez o comentário
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
  public final void setEdited(final Boolean editedBool) {
    this.edited = editedBool;
  }
}
