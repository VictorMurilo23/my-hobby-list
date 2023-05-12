package com.myhobbylistlmtd.springboot.Exceptions;

public class InvalidLoginException extends RuntimeException {
  public InvalidLoginException(String message){
    super(message);
  }
}
