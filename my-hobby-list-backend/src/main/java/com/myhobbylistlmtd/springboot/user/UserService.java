package com.myhobbylistlmtd.springboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;

@Service
public class UserService {
  /**
  * Repositório utilizado para fazer as interações com o banco de dados.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserRepository repository;

  /**
  * Repositório utilizado para fazer as interações com o banco de dados.
  * @param email Email utilizado para buscar os dados do usuário
  no banco de dados.
  * @param password Senha utilizada para saber se é permitido o usuário
  fazer login com sucesso.
  * @return Retorna um token com as informações do usuário
  * @throws InvalidLoginException Ocorre quando o parâmetro password é
  diferente do salvo no banco de dados
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public String validateLogin(
    final String email, final String password
  ) throws InvalidLoginException {
    User currentUser = repository.findByEmail(email);
    if (currentUser == null || !password.equals(currentUser.getPassword())) {
      throw new InvalidLoginException("Senha ou email incorretos");
    }
    String token = "TBA";
    return token;
  }
}
