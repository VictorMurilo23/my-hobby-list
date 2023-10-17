package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RequestCreateReviewComment {
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
   * String sobre o que o autor da review achou sobre a media.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Size(
    min = 1,
    max = 20,
    message = "nome do usuário deve ter um length entre 1 e 20"
  )
  @NotNull(message = "usernameFromReview é obrigatório")
  private String usernameFromReview;

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
  @NotNull(message = "commentary é obrigatório")
  private String commentary;

  /**
   * Getter de mediaId.
   * @return Um número do tipo Long
   */
  public Long getMediaId() {
    return mediaId;
  }

  /**
   * Getter de usernameFromReview.
   * @return Nome do usuário que fez a review.
   */
  public String getUsernameFromReview() {
    return usernameFromReview;
  }

  /**
   * Setter de mediaId.
   * @param mediaIdLong Id de uma media
   */
  public void setMediaId(final Long mediaIdLong) {
    this.mediaId = mediaIdLong;
  }

  /**
   * Setter de usernameFromReview.
   * @param usernameFromReviewStr Nome do usuário que fez a review
   */
  public void setUsernameFromReview(final String usernameFromReviewStr) {
    this.usernameFromReview = usernameFromReviewStr;
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
}
