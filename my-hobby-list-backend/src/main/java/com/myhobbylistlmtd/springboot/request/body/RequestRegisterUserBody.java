package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;

public class RequestRegisterUserBody extends RequestLoginBody {
  /** Atributo username do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @NotNull(message = "username é obrigatório")
  private String username;

  /** Getter do atributo username.
  * @return Retorna o username do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getUsername() {
    return username;
  }

  /** Setter do atributo username.
  * @param newUsername Novo username do body
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setUsername(final String newUsername) {
    this.username = newUsername;
  }
}
