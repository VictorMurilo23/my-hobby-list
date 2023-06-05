package com.myhobbylistlmtd.springboot.user;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.AlreadyTakenException;
import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;
import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;
import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.interfaces.IBasicService;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;

@Service
public class UserService implements IBasicService<User, Long> {
  /**
  * Repositório utilizado para fazer as interações com o banco de dados.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserRepository repository;

  private void validateEmail(final String email) {
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    boolean validate = Pattern.compile(regexPattern).matcher(email).matches();
    if (!validate) {
      throw new BadRequestException("Insira um email com o formato válido!");
    }
  }

  @Override
  public final User findById(final Long id) throws NotFoundException {
    try {
      User media = repository.findById(id).get();
      return media;
    } catch (NoSuchElementException e) {
      throw new NotFoundException("Media não encontrada!");
    }
  }

  /**
  * Repositório utilizado para fazer as interações com o banco de dados.
  * @param body Body da requisição com atributo email e username
  * @throws AlreadyTakenException Ocorre quando é passada
  ou um email ou nome de usuário que já está sendo usado
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  private void validateIfUserExists(
    final RequestRegisterUserBody body
  ) throws AlreadyTakenException {
    User findEmail = this.repository.findByEmail(body.getEmail());
    User findUsername = this.repository.findByUsername(body.getUsername());
    if (findEmail != null) {
      throw new AlreadyTakenException(
        String.format("O email %s já está em uso.", body.getEmail())
      );
    }
    if (findUsername != null) {
      throw new AlreadyTakenException(
        String.format("O nome %s já está em uso.", body.getUsername())
      );
    }
  }

  /**
  * Valida o ato de Login.
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
    this.validateEmail(email);
    User currentUser = repository.findByEmail(email);
    if (currentUser == null || !password.equals(currentUser.getPassword())) {
      throw new InvalidLoginException("Senha ou email incorretos");
    }
    String token = "TBA";
    return token;
  }

  /**
  * Faz o registro de um usuário.
  * @param body Contendo senha, nome de usuário e email.
  no banco de dados.
  fazer login com sucesso.
  * @return Retorna um token com as informações do usuário
  * @throws AlreadyTakenException Ocorre quando é passada
  um email ou nome de usuário que já está sendo usado.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public String registerUser(final RequestRegisterUserBody body) {
    this.validateEmail(body.getEmail());
    this.validateIfUserExists(body);

    User userToBeInserted = new User(
      body.getUsername(), body.getEmail(), body.getPassword()
    );

    repository.save(userToBeInserted);
    String token = "TBA";
    return token;
  }
}
