package com.myhobbylistlmtd.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {
  /**.
  * Exceção lançada quando algum erro relacionado
  ao banco de dados acontece, seja o mesmo estando caído,
  faltando informações etc.
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public InternalErrorException(final String message) {
    super(message);
  }
}
