package com.myhobbylistlmtd.springboot.media.characters;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.characters.role.CharactersRole;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  /**
   * Getter de characterRole.
   * @return Retorna um objeto de CharactersRole
   * contendo o tipo de papel executado pelo personagem.
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
}
