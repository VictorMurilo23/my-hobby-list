package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RequestUserListBody extends RequestUserId {
  /**
   * Id de uma media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @NotNull(message = "mediaId é obrigatório")
  private Long mediaId;

  /**
   * Nome do status.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Size(
    min = 1,
    max = 100,
    message = "status deve ser uma string de 1 a 100 caracteres"
  )
  private String status = null;

  /**
   * Comentário sobre a media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Size(
  min = 1,
  max = 1000,
  message = "notes deve ser uma string de 1 a 1000 caracteres")
  private String notes = null;

  /**
   * Nota de 1 a 10.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Min(value = 1, message = "score deve ser um valor acima de 0")
  @Max(value = 10, message = "score deve ser um valor abaixo de 11")
  @Digits(
    integer = 2,
    fraction = 0,
    message = "score deve ser um número inteiro"
  )
  private Integer score = null;

  /**
   * Progresso feito pelo usuário.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Min(value = 0, message = "progress deve ser maior ou igual a 0")
  @Digits(
    integer = 7,
    fraction = 0,
    message = "progress deve ter no máximo 7 digitos e deve ser um inteiro"
  )
  private Integer progress = null;

  /**
   * Getter do atributo mediaId.
   * @return Um id de uma media que pode ou não existir no banco de dados.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Long getMediaId() {
    System.out.print(mediaId);
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

  /**
   * Getter de progress.
   * @return Um Integer contendo o progresso feito pelo usuário.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Integer getProgress() {
    return progress;
  }

  /**
   * Setter de progress.
   * @param newProgress Um Integer contendo o novo progresso do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setProgress(final Integer newProgress) {
    this.progress = newProgress;
  }
}
