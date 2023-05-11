package com.myhobbylistlmtd.springboot.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.Exceptions.InvalidLoginException;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public String validateLogin(String email, String password) throws InvalidLoginException {
    User currentUser = repository.findByEmail(email);
    if (currentUser == null || !password.equals(currentUser.getPassword())) {
      throw new InvalidLoginException("Senha ou email incorretos");
    }
    String token = "TBA";
    return token;
  }
}
