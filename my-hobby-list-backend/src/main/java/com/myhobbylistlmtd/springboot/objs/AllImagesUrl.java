package com.myhobbylistlmtd.springboot.objs;

import java.util.LinkedList;
import java.util.List;

public class AllImagesUrl {
  /**
   * Lista com a url de todas as imagens disponíveis.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private LinkedList<String> images = new LinkedList<String>();

  /**
   * Getter de images.
   * @return Uma lista com a url de todas as imagens
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public List<String> getImages() {
    return images;
  }

  /**
   * Setter de images.
   * @param imageUrl String com a url de uma imagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setImage(final String imageUrl) {
    this.images.addLast(imageUrl);
  }
}
