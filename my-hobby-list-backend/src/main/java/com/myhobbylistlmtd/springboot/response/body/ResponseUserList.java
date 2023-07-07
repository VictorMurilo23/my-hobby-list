package com.myhobbylistlmtd.springboot.response.body;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.list.list.UserList;
import com.myhobbylistlmtd.springboot.utils.Views;

public class ResponseUserList {
  /**
   * Lista com os itens de userList.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @JsonView(Views.Public.class)
  private List<UserList> list;

  /**
   * Getter do atributo list.
   * @return Retorna uma lista contendo informações dos itens da userList
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public List<UserList> getList() {
    return list;
  }

  /**
   * Setter do atributo list.
   * @param newList Nova lista
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
  */
  public void setList(final List<UserList> newList) {
    this.list = newList;
  }
}
