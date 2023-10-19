package com.myhobbylistlmtd.springboot.media;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.mediacharacters.MediaCharacters;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Media {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(
    {
      Views.Public.class, Views.MediaCard.class,
      Views.ReviewPage.class, Views.UserListItem.class
    }
  )
  private Long id;

  /** Coluna com o nome da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "name", nullable = false)
  @JsonView(
    {
      Views.Public.class, Views.MediaCard.class,
      Views.ReviewPage.class, Views.UserListItem.class
    }
  )
  private String name;

  /** Coluna com a duração da midia, dependendo da midia
  pode ser horas ou capítulos.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "length", nullable = false)
  @JsonView(Views.Public.class)
  private Integer length;

  /** Coluna com a quantidade de volumes que uma midia pode ter,
  * exclusivo para livros e mangas.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "volumes", nullable = true)
  @JsonView(Views.Public.class)
  private Integer volumes = null;

  /** Coluna com a url da capa da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "image_url", nullable = false)
  @JsonView(
    {
      Views.Public.class, Views.MediaCard.class,
      Views.ReviewPage.class, Views.UserListItem.class
    }
  )
  private String image;

  /** Chave estrangeira do id do status atual.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  @JsonView(Views.Public.class)
  private MediaStatus status;

  /** Chave estrangeira do id do status atual.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false, referencedColumnName = "id")
  @JsonView(Views.Public.class)
  private MediaType type;

  /** Data e horário de inserção da media atual.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "insertion_date", nullable = false)
  @JsonView(Views.Internal.class)
  private LocalDateTime insertionDate;

  /**
  * Associação com MediaCharacters.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "media")
  @JsonView(Views.Public.class)
  private Set<MediaCharacters> characters;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Media() { }

  /**
   * Constructor só com o nome da mídia. Geralmente utilizado em testes
   * @param name Nome da mídia
   */
  public Media(final String name) {
    this.name = name;
  }

  /** Cria uma nova midia sem volume.
  * @param name Nome da mídia
  * @param length Duração da mídia
  * @param status Status da mídia
  * @param type Tipo da mídia
  * @param insertionDate Data de inserção da mídia
  * @param image String com a url da imagem da capa da mídia
  * @since 1.0
  * @author Victor Murilo
  * @version 2.0
  */
  public Media(
    final String name,
    final Integer length,
    final MediaStatus status,
    final MediaType type,
    final LocalDateTime insertionDate,
    final String image
  ) {
    this.image = image;
    this.name = name;
    this.length = length;
    this.status = status;
    this.type = type;
    this.insertionDate = insertionDate;
  }

  /** Cria uma nova midia com volume.
  * @param name Nome da mídia
  * @param length Duração da mídia
  * @param status Status da mídia
  * @param type Tipo da mídia
  * @param insertionDate Data de inserção da mídia
  * @param image String com a url da imagem da capa da mídia
  * @param volumes Integer com a quantidade de volumes da mídia
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Media(
    final String name,
    final Integer length,
    final MediaStatus status,
    final MediaType type,
    final LocalDateTime insertionDate,
    final String image,
    final Integer volumes
  ) {
    this.image = image;
    this.name = name;
    this.volumes = volumes;
    this.length = length;
    this.status = status;
    this.type = type;
    this.insertionDate = insertionDate;
  }


  /** Getter do atributo id.
  * @return Retorna um id único da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Long getId() {
    return id;
  }

  /** Getter do atributo name.
  * @return Retorna o nome da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getName() {
    return name;
  }

  /** Setter do atributo name.
  * @param newName Novo nome da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setName(final String newName) {
    this.name = newName;
  }

  /** Getter do atributo length.
  * @return Retorna a duração da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Integer getLength() {
    return length;
  }

  /** Setter do atributo length.
  * @param newLength Nova duração da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setLength(final Integer newLength) {
    this.length = newLength;
  }

  /** Getter do atributo volumes.
  * @return Retorna a quantidade de volumes de uma midia ou null.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Integer getVolumes() {
    return volumes;
  }

  /** Setter do atributo volumes.
  * @param newVolumes Nova quantidade de volumes da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setVolumes(final Integer newVolumes) {
    this.volumes = newVolumes;
  }

  /** Getter do atributo status.
  * @return Retorna o status atual da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaStatus getStatus() {
    return status;
  }

  /** Setter do atributo status.
  * @param newStatus Novo status da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setStatus(final MediaStatus newStatus) {
    this.status = newStatus;
  }

  /** Getter do atributo type.
  * @return Retorna tipo da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaType getType() {
    return type;
  }

  /** Setter do atributo type.
  * @param newType Novo status da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setType(final MediaType newType) {
    this.type = newType;
  }

  /** Getter do atributo image.
  * @return Retorna a url da imagem da capa.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getImage() {
    return image;
  }

  /** Setter do atributo image.
  * @param imageUrl Nova url da capa da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setImage(final String imageUrl) {
    this.image = imageUrl;
  }

  /** Getter do atributo insertionDate.
  * @return Um objeto LocalDateTime
  com a data e horário em que a media foi inserida no DB.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public LocalDateTime getInsertionDate() {
    return insertionDate;
  }

  /** Setter do atributo insertionDate.
  * @param newInsertionDate Data e horário de inserção da media, deve ser
  um objeto do tipo LocalDateTime.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setInsertionDate(final LocalDateTime newInsertionDate) {
    this.insertionDate = newInsertionDate;
  }
}
