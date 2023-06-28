package com.myhobbylistlmtd.springboot.user;

import java.util.NoSuchElementException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.AlreadyTakenException;
import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;
import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;
import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.interfaces.IBasicService;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

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

  /**
   * key usada ao gerar um token.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private SecretKey key;

  UserService() {
    String secret = System.getenv("JWT_SECRET");
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  @Override
  public final User findById(final Long id) throws NotFoundException {
    try {
      User user = repository.findById(id).get();
      return user;
    } catch (NoSuchElementException e) {
      throw new NotFoundException("User não encontrada!");
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
  public String registerUser(
    final RequestRegisterUserBody body
  ) throws AlreadyTakenException {
    this.validateIfUserExists(body);

    User userToBeInserted = new User(
      body.getUsername(), body.getEmail(), body.getPassword()
    );

    User savedUser = repository.save(userToBeInserted);
    String token = this.generateJwtToken(
      savedUser.getId(),
      savedUser.getUsername()
    );
    this.verifyToken(token);
    return token;
  }

  /**
   * Retorna um token contendo o id e username de um usuário.
   * @param userId Id que vai ser armazenado no token
   * @param username username que vai ser armazenado no token
   * @return Um token jwt
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private String generateJwtToken(
    final Long userId, final String username
  ) {
    String jwt = Jwts.builder()
      .signWith(this.key, SignatureAlgorithm.HS256)
      .claim("username", username)
      .claim("id", userId)
      .compact();
    return jwt;
  }

  /**
   * Verifica o token e retorna o id do usuário.
   * @param token Uma string contendo um token jwt.
   * @return Uma string com o id do usuário.
   * @throws BadRequestException Ocorre quando o token é inválido.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String verifyToken(final String token) throws BadRequestException {
    try {
      Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(this.key)
        .build()
        .parseClaimsJws(token);
      return jwt.getBody().get("id").toString();
    } catch (Exception e) {
      throw new BadRequestException("Token inválido");
    }
  }
}
