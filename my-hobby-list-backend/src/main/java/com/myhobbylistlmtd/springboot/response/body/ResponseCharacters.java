package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharacters;
import com.myhobbylistlmtd.springboot.utils.Views;

public class ResponseCharacters {
  /**
   * Lista contendo os personagens referentes a uma media.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @JsonView(Views.Public.class)
  private List<MediaCharacters> characters;

  /**
   * Getter de characters.
   * @return Uma lista contendo os personagens
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public List<MediaCharacters> getCharacters() {
    return characters;
  }

  /**
   * Setter de characters.
   * @param charactersList Lista com personagens
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setCharacters(final List<MediaCharacters> charactersList) {
    this.characters = charactersList;
  }
}
