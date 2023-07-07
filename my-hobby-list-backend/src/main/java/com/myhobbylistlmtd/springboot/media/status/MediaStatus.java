package com.myhobbylistlmtd.springboot.media.status;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_status")
public class MediaStatus {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(Views.Internal.class)
  private Long id;

  /** Nome do tipo de status da midia.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "status_name", nullable = false)
  @JsonView(Views.Public.class)
  @JsonValue
  private String status;

  /** Associação com tabela media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "status")
  private Set<Media> media;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaStatus() { }

  /** Cria um novo status de midia.
  * @param statusName Nome do status
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaStatus(final String statusName) {
    this.status = statusName;
  }

  /** Getter do atributo id.
  * @return Retorna um id único do status.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Long getId() {
    return id;
  }

  /** Getter do atributo status.
  * @return Retorna o nome do status.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getStatus() {
    return status;
  }

  /** Setter do atributo status.
  * @param statusName Novo nome do status.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setStatus(final String statusName) {
    this.status = statusName;
  }
}
