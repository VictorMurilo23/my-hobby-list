package com.myhobbylistlmtd.springboot.response.body;

public class ResponseLoginBody {
  /**
   * Token que será enviado para o front-end.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  private String token;

  /** Getter do atributo token.
  * @return Retorna o token do body.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getToken() {
    return token;
  }

  /** Setter do atributo email.
  * @param resToken Novo token do body
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setToken(final String resToken) {
    this.token = resToken;
  }
}
