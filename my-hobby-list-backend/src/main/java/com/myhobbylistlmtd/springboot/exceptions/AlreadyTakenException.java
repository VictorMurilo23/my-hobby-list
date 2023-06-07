package com.myhobbylistlmtd.springboot.exceptions;

public class AlreadyTakenException extends RuntimeException {
  /**.
  * Exceção lançada quando o email ou nome de usuário já estão em uso
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public AlreadyTakenException(final String message) {
    super(message);
  }
}
