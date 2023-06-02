package com.myhobbylistlmtd.springboot.list.list;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
}
