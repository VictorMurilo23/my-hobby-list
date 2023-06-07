package com.myhobbylistlmtd.springboot.request.body;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RequestLoginBody {
  /** Atributo email do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Email(message = "Insira um email com o formato válido!")
  @NotNull(message = "email é obrigatório")
  private String email;

  /** Atributo password do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @NotNull(message = "password é obrigatório")
  private String password;

  /** Getter do atributo email.
  * @return Retorna o email do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getEmail() {
    return email;
  }

  /** Setter do atributo email.
  * @param userEmail Novo email do body
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setEmail(final String userEmail) {
    this.email = userEmail;
  }

  /** Getter do atributo password.
  * @return Retorna o password do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getPassword() {
    return password;
  }

  /** Setter do atributo password.
  * @param userPassword Novo password do body
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setPassword(final String userPassword) {
    this.password = userPassword;
  }
}
