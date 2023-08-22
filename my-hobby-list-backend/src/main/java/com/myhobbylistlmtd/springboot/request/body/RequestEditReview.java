package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RequestEditReview {
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
    max = 10000,
    message = "content deve ter um length entre 1 e 10000"
  )
  private String content;

  /**
   * boolean demostrando se o autor da review recomenda ou não a media.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  private Boolean recommended;

  /**
   * Getter de content.
   * @return Conteúdo da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
  */
  public String getContent() {
    return content;
  }

  /**
   * Getter de mediaId.
   * @return mediaId da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Long getMediaId() {
    return mediaId;
  }

  /**
   * Getter de recommended.
   * @return boolean dizendo se o autor da review recomenda a media ou não
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Boolean getRecommended() {
    return recommended;
  }

  /**
   * Setter de content.
   * @param contentString String com o conteúdo da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setContent(final String contentString) {
    this.content = contentString;
  }

  /**
   * Setter de mediaId.
   * @param mediaIdLong mediaId
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setMediaId(final Long mediaIdLong) {
    this.mediaId = mediaIdLong;
  }

  /**
   * Setter de recommended.
   * @param recommendedBoolean boolean
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setRecommended(final Boolean recommendedBoolean) {
    this.recommended = recommendedBoolean;
  }
}
