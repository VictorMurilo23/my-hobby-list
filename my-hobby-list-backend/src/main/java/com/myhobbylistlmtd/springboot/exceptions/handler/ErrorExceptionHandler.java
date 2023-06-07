package com.myhobbylistlmtd.springboot.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myhobbylistlmtd.springboot.exceptions.AlreadyTakenException;
import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;
import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;
import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;

@RestControllerAdvice
public class ErrorExceptionHandler {
  static class Error {
    /**
     * Status code do error.
     * @since 1.0
     * @version 1.0
     * @author Victor Murilo
     */
    private final Integer status;

    /**
     * Mensagem gerada pelo erro.
     * @since 1.0
     * @version 1.0
     * @author Victor Murilo
     */
    private final String message;

    Error(final Integer status, final String message) {
      this.status = status;
      this.message = message;
    }

    public Integer getStatus() {
      return status;
    }

    public String getMessage() {
      return message;
    }
  }

  /**
   * Tratamento de erros relacionados a validação do body.
   * @param ex Exceção de validação
   * @return Um objeto de Error contendo o status code e a mensagem do erro.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Error handleValidationErrors(
    final MethodArgumentNotValidException ex
  ) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();

    return new Error(
      HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage()
    );
  }

  /**
   * Tratamento de erros relacionados ao não encontrar valores.
   * @param ex Exceção de NotFound
   * @return Um objeto de Error contendo o status code e a mensagem do erro.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Error handleNotFoundErrors(
    final NotFoundException ex
  ) {
    return new Error(
      HttpStatus.NOT_FOUND.value(), ex.getMessage()
    );
  }

  /**
   * Tratamento de erros relacionados a valores já presentes no banco de dados.
   * @param ex Exceção de AlreadyTaken
   * @return Um objeto de Error contendo o status code e a mensagem do erro.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @ExceptionHandler(AlreadyTakenException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public Error handleAlreadyTakenErrors(
    final AlreadyTakenException ex
  ) {
    return new Error(
      HttpStatus.CONFLICT.value(), ex.getMessage()
    );
  }

    /**
   * Tratamento de erros relacionados a erros no request do user.
   * @param ex Exceção de BadRequestException
   * @return Um objeto de Error contendo o status code e a mensagem do erro.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Error handleBadRequestErrors(
    final BadRequestException ex
  ) {
    return new Error(
      HttpStatus.BAD_REQUEST.value(), ex.getMessage()
    );
  }

  /**
   * Tratamento de erros relacionados a erros no login do user.
   * @param ex Exceção de InvalidLoginException
   * @return Um objeto de Error contendo o status code e a mensagem do erro.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @ExceptionHandler(InvalidLoginException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Error handleBadRequestErrors(
    final InvalidLoginException ex
  ) {
    return new Error(
      HttpStatus.UNAUTHORIZED.value(), ex.getMessage()
    );
  }
}
