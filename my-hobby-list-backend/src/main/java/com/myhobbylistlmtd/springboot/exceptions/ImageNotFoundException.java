package com.myhobbylistlmtd.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
  /**.
  * Exceção lançada quando a imagem requisitada não é encontrada
  * @param message
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public ImageNotFoundException(final String message) {
    super(message);
  }
}
