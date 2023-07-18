package com.myhobbylistlmtd.springboot.media.characters;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.characters.Characters;
import com.myhobbylistlmtd.springboot.characters.role.CharactersRole;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_characters")
public class MediaCharacters {
  /**
   * Id composto de chave estrangeiras.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @EmbeddedId
  @JsonView(Views.Public.class)
  private MediaCharactersId id;

  /**
   * dwa.
   */
  @ManyToOne
  @MapsId("characterId")
  @JoinColumn(name = "character_id")
  private Characters character;

  /**
   * caidh.
   */
  @ManyToOne
  @MapsId("mediaId")
  @JoinColumn(name = "media_id")
  private Media media;

  /**
   * Atributo contendo o papel executado pelo personagem.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
  private CharactersRole characterRole;

  /**
   * Getter de id.
   * @return Retorna o id.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public MediaCharactersId getId() {
    return id;
  }

/** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaCharacters() { }

  /**
   * Constructor.
   * @param media Media a ser relacionado com um personagem
   * @param character Personagem
   * @param role Papel executado por esse personagem
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public MediaCharacters(
    final Media media, final Characters character, final CharactersRole role
  ) {
    this.character = character;
    this.media = media;
    this.characterRole = role;
  }

  /**
   * Getter de characterRole.
   * @return Retorna um objeto de CharactersRole
   *         contendo o tipo de papel executado pelo personagem.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public CharactersRole getCharacterRole() {
    return characterRole;
  }

  /**
   * Setter de characterRole.
   * @param role Um objeto contendo o papel executado pelo personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setCharacterRole(final CharactersRole role) {
    this.characterRole = role;
  }

  /**
   * Getter de character.
   * @return Retorna o personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Characters getCharacter() {
    return character;
  }

  /**
   * Setter de character.
   * @param newCharacter Personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setCharacter(final Characters newCharacter) {
    this.character = newCharacter;
  }

  /**
   * Getter de media.
   * @return Retorna a media
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Media getMedia() {
    return media;
  }

  /**
   * Setter de media.
   * @param newMedia Media
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setMedia(final Media newMedia) {
    this.media = newMedia;
  }
}
