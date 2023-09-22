package com.myhobbylistlmtd.springboot.charactersrole;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharacters;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "character_role")
public class CharactersRole {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView({Views.Internal.class})
  private Long id;

  /**
   * Nome do papel feito pelo personagem.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Column(name = "role_name")
  @JsonView({Views.Public.class})
  @JsonValue
  private String roleName;

   /** Associação com tabela media_characters.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "characterRole")
  private Set<MediaCharacters> mediaCharacters;

  /**
   * Default Constructor.
   * @since 1.0
  * @author Victor Murilo
  * @version 1.0
   */
  public CharactersRole() { }

  /**
   * Constructor.
   * @param name Nome do papel executado
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public CharactersRole(final String name) {
    this.roleName = name;
  }

  /**
   * Getter de id.
   * @return Retorna o id do role
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Long getId() {
    return id;
  }

  /**
   * Getter de roleName.
   * @return Retorna uma string com o nome do papel executado pelo personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * Getter de roleName.
   * @param name Uma string com o nome do papel executado por um personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setRoleName(final String name) {
    this.roleName = name;
  }
}
