package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.NotNull;

public class RequestUserId {
  /**
   * Id do usuário.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @NotNull(message = "userId é obrigatório")
  private Long userId;

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

}
