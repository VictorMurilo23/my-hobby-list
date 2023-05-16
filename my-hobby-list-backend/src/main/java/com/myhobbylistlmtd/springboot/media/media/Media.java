package com.myhobbylistlmtd.springboot.media.media;

import com.myhobbylistlmtd.springboot.media.status.MediaStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
public class Media {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Coluna com o nome da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "name", nullable = false)
  private String name;

  /** Coluna com a duração da midia, dependendo da midia
  pode ser horas ou capítulos.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "length", nullable = false)
  private Integer length;

  /** Coluna com a quantidade de volumes que uma midia pode ter,
  * exclusivo para livros e mangas.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "volumes", nullable = true)
  private Integer volumes;

  /** Coluna com a quantidade de volumes que uma midia pode ter,
  * exclusivo para livros e mangas.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToOne
  @JoinColumn(name = "status_id", nullable = false)
  // @Resource(path = "libraryAddress", rel = "address")
  private MediaStatus status;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Media() { }

  /** Cria uma nova midia.
  * @param name Nome da midia
  * @param length Duração da midia
  * @param volumes Quantidade de volumes se a midia
  for livro ou manga, se não for bota null
  * @param status Status da midia, relacionado a tabela media_status.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Media(
    final String name, final Integer length,
    final Integer volumes, final MediaStatus status
  ) {
    this.name = name;
    this.length = length;
    this.volumes = volumes;
    this.status = status;
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

  /** Setter do atributo volumes.
  * @param newStatus Novo status da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setStatus(final MediaStatus newStatus) {
    this.status = newStatus;
  }
}
