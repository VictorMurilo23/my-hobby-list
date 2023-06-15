package com.myhobbylistlmtd.springboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseLoginBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public final class UserController {
  /**
  * Serviço do user, responsável pelas regras de negócio.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserService service;

  /**
  * Função da rota de login, recebe a senha e email de um usuário e,
  caso de tudo certo, retorna um token.
  * @param body Recebe um body contendo a senha e o email do usuário.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @PostMapping("/login")
  ResponseLoginBody validateLogin(
    @Valid @RequestBody final RequestLoginBody body
  ) {
    String token = service.validateLogin(body.getEmail(), body.getPassword());
    ResponseLoginBody response = new ResponseLoginBody();
    response.setToken(token);
    return response;
  }

  /**
  * Função da rota de cadastro, recebe umaa senha, nome de usuário e email e,
  caso de tudo certo, retorna um token.
  * @param body Recebe um body contendo a senha, nome do usuário e o email.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseLoginBody registerUser(
    @Valid @RequestBody final RequestRegisterUserBody body
  ) {
    String token = service.registerUser(body);
    ResponseLoginBody response = new ResponseLoginBody();
    response.setToken(token);
    return response;
  }
}
