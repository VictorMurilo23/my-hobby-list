package com.myhobbylistlmtd.springboot.response.body;

public class ResponseMessage {
    /**
   * Lista com todas as medias.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  private String message;

  /**
   * Getter do atributo message.
   * @return Uma string contendo uma mensagem
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public String getMessage() {
    return message;
  }

  /**
   * Setter do atributo message.
   * @param string Uma string contendo uma mensagem.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setMessage(final String string) {
    this.message = string;
  }
}
