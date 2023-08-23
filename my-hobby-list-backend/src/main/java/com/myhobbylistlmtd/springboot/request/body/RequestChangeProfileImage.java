package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;

public class RequestChangeProfileImage {
  /**
   * mediaId referente a review.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @NotNull(message = "imageUrl deve ser um valor válido")
  private String imageUrl;

  /**
   * Getter de imageUrl.
   * @return Uma string com a url da imagem
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Setter de imageUrl.
   * @param imageUrlStr String com a url da imagem
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setImageUrl(final String imageUrlStr) {
    this.imageUrl = imageUrlStr;
  }
}
