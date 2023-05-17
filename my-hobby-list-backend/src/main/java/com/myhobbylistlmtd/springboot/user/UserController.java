package com.myhobbylistlmtd.springboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myhobbylistlmtd.springboot.exceptions.AlreadyTakenException;
import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;
import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseLoginBody;

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
  * @throws InvalidLoginException Essa exceção ocorre quando o
  email não é encontrado no banco de dados ou quando a senha passada
  é diferente da salva no banco de dados.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @GetMapping("/login")
  ResponseLoginBody validateLogin(@RequestBody final RequestLoginBody body) {
    try {
      String token = service.validateLogin(body.getEmail(), body.getPassword());
      ResponseLoginBody response = new ResponseLoginBody();
      response.setToken(token);
      return response;
    } catch (InvalidLoginException exc) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED, exc.getMessage()
      );
    }
  }

  /**
  * Função da rota de cadastro, recebe umaa senha, nome de usuário e email e,
  caso de tudo certo, retorna um token.
  * @param body Recebe um body contendo a senha, nome do usuário e o email.
  * @throws AlreadyTakenException Essa exceção ocorre o email
  já está em uso ou quando o nome de usuário ja está em uso.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseLoginBody registerUser(
    @RequestBody final RequestRegisterUserBody body
  ) {
    try {
      String token = service.registerUser(body);
      ResponseLoginBody response = new ResponseLoginBody();
      response.setToken(token);
      return response;
    } catch (AlreadyTakenException exc) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT, exc.getMessage()
      );
    }
  }
}
