package com.myhobbylistlmtd.springboot.Exceptions;

public class InvalidLoginException extends RuntimeException {
  /**.
  * Recebe uma mensagem que irá ser exibida no response de erro
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public InvalidLoginException(final String message) {
    super(message);
  }
}
