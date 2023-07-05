package com.myhobbylistlmtd.springboot.list.status;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.list.list.UserList;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  @JsonView(Views.Internal.class)
  private Long id;


  /** Nome do tipo de status do item da lista.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "list_status_name", nullable = false)
  @JsonView(Views.Public.class)
  @JsonValue
  private String statusName;

  /** Associação com tabela user_list.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "status")
  private Set<UserList> media;

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
