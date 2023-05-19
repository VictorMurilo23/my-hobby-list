package com.myhobbylistlmtd.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
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
