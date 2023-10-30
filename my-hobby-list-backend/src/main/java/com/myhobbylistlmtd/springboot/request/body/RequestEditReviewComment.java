package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RequestEditReviewComment {
  /**
   * mediaId referente a review.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @NotNull(message = "mediaId deve ser um valor válido")
  @Positive(message = "mediaId deve ser um número positivo")
  private Long mediaId;

 /**
   * Id do comentário.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @NotNull(message = "commentId deve ser um valor válido")
  @Positive(message = "commentId deve ser um número positivo")
  private Long commentId;

  /**
   * String sobre o que o autor da review achou sobre a media.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Size(
    min = 1,
    max = 1000,
    message = "comentário deve ter um length entre 1 e 1000"
  )
  @NotNull(message = "commentary deve ser um valor válido")
  private String commentary = null;

  /**
   * Getter de mediaId.
   * @return Um número do tipo Long
   */
  public Long getMediaId() {
    return mediaId;
  }

  /**
   * Setter de mediaId.
   * @param mediaIdLong Id de uma media
   */
  public void setMediaId(final Long mediaIdLong) {
    this.mediaId = mediaIdLong;
  }

  /**
   * Getter de commentary.
   * @return String contendo o comentário feito pelo usuário
   */
  public String getCommentary() {
    return commentary;
  }

    /**
   * Setter de commentary.
   * @param commentaryStr Comentário feito pelo usuário
   */
  public void setCommentary(final String commentaryStr) {
    this.commentary = commentaryStr;
  }

  /**
   * Getter de commentId.
   * @return Long com o id do comentário
   */
  public Long getCommentId() {
    return commentId;
  }

  /**
   * Setter de commentary.
   * @param commentIdLong Id do comentário
   */
  public void setCommentId(final Long commentIdLong) {
    this.commentId = commentIdLong;
  }

}
