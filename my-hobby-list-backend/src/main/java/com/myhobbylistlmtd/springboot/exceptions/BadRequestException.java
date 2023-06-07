package com.myhobbylistlmtd.springboot.exceptions;

public class BadRequestException extends RuntimeException {
  /**.
  * Exceção lançada quando o usuário manda informações no formato incorreto.
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public BadRequestException(final String message) {
    super(message);
  }
}
