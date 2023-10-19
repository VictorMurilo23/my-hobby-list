package com.myhobbylistlmtd.springboot.characters;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
   * Url da imagem do personagem.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Column(name = "character_image_url")
  @JsonView({Views.Public.class})
  private String characterImageUrl;

  /**
  * Associação com MediaCharacters.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "character")
  @JsonIgnore
  private Set<MediaCharacters> mediaCharacters;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Characters() { }

  /**
   * Constructor.
   * @param name Nome do personagem
   * @param imageUrl Url da imagem do personagem
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Characters(
    final String name,
    final String imageUrl
  ) {
    this.name = name;
    this.characterImageUrl = imageUrl;
  }

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
   * Setter de name.
   * @param charName Uma string com o nome do personagem
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setName(final String charName) {
    this.name = charName;
  }

  /**
   * Getter de characterImageUrl.
   * @return Uma string com a url da imagem do personagem
   */
  public String getCharacterImageUrl() {
    return characterImageUrl;
  }

  /**
   * Setter de characterImageUrl.
   * @param characterImageUrlStr String com a url da imagem do personagem
   */
  public void setCharacterImageUrl(final String characterImageUrlStr) {
    this.characterImageUrl = characterImageUrlStr;
  }
}
