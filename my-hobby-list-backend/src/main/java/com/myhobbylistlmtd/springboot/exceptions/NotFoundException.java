package com.myhobbylistlmtd.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  /**.
  * Exceção lançada quando algum elemento obrigatório
  não é encontrado no banco de dados.
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public NotFoundException(final String message) {
    super(message);
  }
}
