package com.myhobbylistlmtd.springboot.media.characters;

import java.io.Serializable;
import java.util.Objects;

import com.myhobbylistlmtd.springboot.characters.Characters;
import com.myhobbylistlmtd.springboot.media.media.Media;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class MediaCharactersId implements Serializable {
  /**
  * Chave referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "media_id")
  private Media media;

  /**
  * Chave referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "character_id")
  private Characters character;

  /**
  * Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaCharactersId() {
  }

  /**
  * Default constructor.
  * @param character Id referente ao personagem.
  * @param media Id referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaCharactersId(final Characters character, final Media media) {
    this.character = character;
    this.media = media;
  }

  /**
   * Getter de character.
   * @return Retorna um objeto de Characters
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Characters getCharacter() {
    return character;
  }

  /**
   * Getter de media.
   * @return Retorna um objeto de Media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Media getMedia() {
    return media;
  }

  /**
   * Setter de character.
   * @param characterId Um objeto de Characters
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setCharacter(final Characters characterId) {
    this.character = characterId;
  }

  /**
   * Setter de media.
   * @param mediaId Um objeto de Media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setMedia(final Media mediaId) {
    this.media = mediaId;
  }

  /**
   * Cria uma hash do objeto atual.
   * @return Uma hash.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public int hashCode() {
    return Objects.hash(character, media);
  }

    /**
   * Compara o objeto atual com outro.
   * @return Um boolean dizendo se o objeto é igual ou não
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MediaCharactersId other = (MediaCharactersId) obj;
    return Objects.equals(character, other.character)
    && Objects.equals(media, other.media);
  }
}
