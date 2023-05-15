package com.myhobbylistlmtd.springboot.request.body;

// TODO Usar essa classe quando for fazer o body de cadastro.
public class RequestLoginBody {
  /** Atributo email do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private String email;

  /** Atributo password do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
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
