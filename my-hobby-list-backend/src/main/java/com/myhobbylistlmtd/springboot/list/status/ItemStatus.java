package com.myhobbylistlmtd.springboot.list.status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "list_item_status")
public class ItemStatus {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  /** Nome do tipo de status do item da lista.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "list_status_name", nullable = false)
  private String statusName;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public ItemStatus() { }

  /** Cria um novo status de item da lista.
  * @param statusName Nome do status
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public ItemStatus(final String statusName) {
    this.statusName = statusName;
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

  /** Getter do atributo statusName.
  * @return Retorna o nome do status de item da lista.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getStatusName() {
    return statusName;
  }

  /** Setter do atributo statusName.
  * @param newStatusName Uma string contendo o nome do status.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setStatusName(final String newStatusName) {
    this.statusName = newStatusName;
  }
}
