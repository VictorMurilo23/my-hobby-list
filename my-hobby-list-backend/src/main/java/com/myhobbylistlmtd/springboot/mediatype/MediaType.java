package com.myhobbylistlmtd.springboot.mediatype;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_type")
public class MediaType {
  /** Length máxima permitida para o nome do tipo de midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private static final int TYPE_LENGTH = 45;

  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(Views.Internal.class)
  private Long id;

  /** Coluna com o nome do tipo de
  midia, pode ser jogo, livro, anime, manga etc.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "type", length = TYPE_LENGTH, nullable = false, unique = true)
  @JsonView(Views.Public.class)
  @JsonValue
  private String type;

  /** Associação com tabela media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "type")
  private Set<Media> media;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaType() { }

  /** Objeto de tipo de midia.
  * @param type Nome do tipo de midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaType(final String type) {
    this.type = type;
  }

  /** Getter do atributo id.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @return Retorna o id do tipo de midia.
  */
  public final Long getId() {
    return id;
  }

  /** Getter do atributo type.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @return Retorna o nome do tipo da midia.
  */
  public final String getType() {
    return type;
  }

  /** Setter do atributo type.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @param mediaType Novo nome do tipo de midia.
  */
  public final void setType(final String mediaType) {
    this.type = mediaType;
  }
}
