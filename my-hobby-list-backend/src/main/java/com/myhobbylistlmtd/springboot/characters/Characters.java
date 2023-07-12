package com.myhobbylistlmtd.springboot.characters;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "characters")
public class Characters {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView({Views.Public.class})
  private Long id;

  /**
   * Nome do personagem.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Column(name = "name", length = 1000)
  @JsonView({Views.Public.class})
  private String name;

  /**
   * Descrição do personagem.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Column(name = "character_info", length = 1000)
  @JsonView({Views.Public.class})
  private String characterInfo;

  /**
   * Getter de characterInfo.
   * @return A descrição do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getCharacterInfo() {
    return characterInfo;
  }

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Characters() { }

  /**
   * Getter de id.
   * @return Retorna o id do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Long getId() {
    return id;
  }

  /**
   * Getter de name.
   * @return Retorna o nome do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getName() {
    return name;
  }

  /**
   * Setter de characterInfo.
   * @param info Uma string com a descrição do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setCharacterInfo(final String info) {
    this.characterInfo = info;
  }

  /**
   * Setter de name.
   * @param charName Uma string com o nome do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setName(final String charName) {
    this.name = charName;
  }
}
