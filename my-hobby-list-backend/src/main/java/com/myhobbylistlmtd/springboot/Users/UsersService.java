package com.myhobbylistlmtd.springboot.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.Exceptions.InvalidLoginException;

@Service
public class UsersService {
  @Autowired
  private UsersRepository repository;

  public String validateLogin(String email, String password) throws InvalidLoginException {
    Users currentUser = repository.findByEmail(email);
    if (currentUser == null || !password.equals(currentUser.getPassword())) {
      throw new InvalidLoginException("Senha ou email incorretos");
    }
    String token = "TBA";
    return token;
  }
}
