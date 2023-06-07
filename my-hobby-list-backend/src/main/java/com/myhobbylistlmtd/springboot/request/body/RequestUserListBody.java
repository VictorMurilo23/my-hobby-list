package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestUserListBody {
  /**
   * Id de uma media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @NotNull(message = "mediaId é obrigatório")
  private Long mediaId;

  /**
   * Id do usuário.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @NotNull(message = "userId é obrigatório")
  private Long userId;

  /**
   * Nome do status.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @NotBlank
  private String status = "Em andamento";

  /**
   * Comentário sobre a media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private String notes = null;

  /**
   * Nota de 1 a 10.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Min(value = 1, message = "Insira a nota deve ser um valor acima de 0")
  @Max(value = 10, message = "Insira a nota deve ser um valor abaixo de 11")
  private Integer score = null;

  /**
   * Getter do atributo mediaId.
   * @return Um id de uma media que pode ou não existir no banco de dados.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Long getMediaId() {
    return mediaId;
  }

  /**
   * Setter do atributo mediaId.
   * @param id Um mediaId do tipo Long.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setMediaId(final Long id) {
    this.mediaId = id;
  }

  /**
   * Getter do atributo userId.
   * @return Retorna um id de usuário do tipo Long.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Setter do atributo userId.
   * @param id Um userId do tipo Long.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setUserId(final Long id) {
    this.userId = id;
  }

  /**
   * Getter do atributo status.
   * @return Retorna uma string contendo o nome do status.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getStatus() {
    return status;
  }

  /**
   * Setter do atributo status.
   * @param statusName Nome do status a ser inserido no banco de dados.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setStatus(final String statusName) {
    this.status = statusName;
  }

  /**
   * Getter do atributo notes.
   * @return Retorna o comentário feito sobre a media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public final String getNotes() {
    return notes;
  }

  /**
   * Setter do atributo notes.
   * @param newNotes Uma string contendo os comentários sobre a media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public final void setNotes(final String newNotes) {
    this.notes = newNotes;
  }

  /**
   * Getter do atributo score.
   * @return Retorna a nota dada a media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public final Integer getScore() {
    return score;
  }

  /**
   * Setter do atributo score.
   * @param scoreNum Uma nota de no minímo 1 e no máximo 10.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public final void setScore(final Integer scoreNum) {
    this.score = scoreNum;
  }
}
