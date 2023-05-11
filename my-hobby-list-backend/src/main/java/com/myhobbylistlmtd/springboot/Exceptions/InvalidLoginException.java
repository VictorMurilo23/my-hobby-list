package com.myhobbylistlmtd.springboot.Exceptions;

public class InvalidLoginException extends Exception {
  public InvalidLoginException(String message){
    super(message);
  }
}
