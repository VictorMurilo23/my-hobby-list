package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.myhobbylistlmtd.springboot.media.media.Media;

public class ResponseMediasList {
  /**
   * Lista com todas as medias.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  private List<Media> medias;

  /** Getter do atributo token.
  * @return Retorna uma lista com todas as medias.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public List<Media> getMedias() {
    return medias;
  }

  /** Setter do atributo medias.
  * @param mediasList Uma lista com todas as listas a serem salvas.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setMedias(final List<Media> mediasList) {
    this.medias = mediasList;
  }
}
