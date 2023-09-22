package com.myhobbylistlmtd.springboot.mediacharacters;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MediaCharactersId implements Serializable {
  /**
  * Chave referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "media_id")
  private Long mediaId;

  /**
  * Chave referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "character_id")
  private Long characterId;

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
  public MediaCharactersId(final Long character, final Long media) {
    this.characterId = character;
    this.mediaId = media;
  }

  /**
   * Getter de character.
   * @return Retorna um objeto de Characters
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Long getCharacterId() {
    return characterId;
  }

  /**
   * Getter de media.
   * @return Retorna um objeto de Media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Long getMediaId() {
    return mediaId;
  }

  /**
   * Setter de character.
   * @param id Um objeto de Characters
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setCharacterId(final Long id) {
    this.characterId = id;
  }

  /**
   * Setter de media.
   * @param id Um objeto de Media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setMediaId(final Long id) {
    this.mediaId = id;
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
    return Objects.hash(characterId, mediaId);
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
    return Objects.equals(characterId, other.characterId)
    && Objects.equals(mediaId, other.mediaId);
  }
}
