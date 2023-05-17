package com.myhobbylistlmtd.springboot.exceptions;

public class InvalidLoginException extends RuntimeException {
  /**.
  * Exceção lançada ao falhar em fazer Login, seja por senha ou email incorretos
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public InvalidLoginException(final String message) {
    super(message);
  }
}
