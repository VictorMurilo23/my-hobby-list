package com.myhobbylistlmtd.springboot.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myhobbylistlmtd.springboot.RequestBodys.RequestLoginBody;
import com.myhobbylistlmtd.springboot.ResponseBodys.ResponseLoginBody;

@RestController
public class UsersController {
  @Autowired
  private UsersService service;

  @GetMapping("/login")
  ResponseLoginBody validateLogin(@RequestBody RequestLoginBody body) {
    try {
      String token = service.validateLogin(body.email, body.password);
      return new ResponseLoginBody(token);
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exc.getMessage());
    }
  }
}
