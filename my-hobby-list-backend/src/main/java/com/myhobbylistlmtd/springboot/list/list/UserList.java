package com.myhobbylistlmtd.springboot.list.list;

import com.myhobbylistlmtd.springboot.list.status.ItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_list")
public class UserList {
  /** Length máxima permitida para o usuário expressar
  a própria opnião da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private static final int NOTES_LENGTH = 1000;

  /**
   * Id composto de chave estrangeiras.
   */
  @EmbeddedId
  private UserListId id;

  /** Campo do progresso feito.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "progress", nullable = false)
  private Integer progress;

  /** Nota dada pelo o usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "score", nullable = true)
  private Integer score;

  /** Comentários do usuário sobre a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "notes", nullable = true, length = NOTES_LENGTH)
  private String notes;

  /** Chave estrangeira referente ao status do item.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(
    name = "list_item_status_id",
    nullable = false,
    referencedColumnName = "id"
  )
  private ItemStatus status;

  /**
   * Getter do atributo Id.
   * @return Id do item atual.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public UserListId getId() {
    return id;
  }

  /**
   * Setter do atributo id.
   * @param newId Objeto do tipo UserListId
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setId(final UserListId newId) {
    this.id = newId;
  }

    /**
   * Getter do atributo notes.
   * @return String com comentários sobre a media.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Setter do atributo notes.
   * @param newNotes String contendo comentários.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setNotes(final String newNotes) {
    this.notes = newNotes;
  }

    /**
   * Getter do atributo progress.
   * @return Int com o progresso do usuário.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Integer getProgress() {
    return progress;
  }

    /**
   * Setter do atributo progress.
   * @param newProgress Int com o novo progresso.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setProgress(final Integer newProgress) {
    this.progress = newProgress;
  }

    /**
   * Getter do atributo score.
   * @return Int com a nota dada pelo usuário.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Integer getScore() {
    return score;
  }

  /**
   * Setter do atributo score.
   * @param newScore Um Int entre 0 e 10.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setScore(final Integer newScore) {
    this.score = newScore;
  }

  /**
   * Getter do atributo status.
   * @return Um objeto de ItemStatus.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public ItemStatus getStatus() {
    return status;
  }

    /**
   * Setter do atributo status.
   * @param newStatus Um objeto de ItemStatus contendo o status.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setStatus(final ItemStatus newStatus) {
    this.status = newStatus;
  }
}
