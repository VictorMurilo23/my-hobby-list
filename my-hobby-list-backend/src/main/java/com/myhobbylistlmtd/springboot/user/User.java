package com.myhobbylistlmtd.springboot.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  /** Length máxima permitida para o nome do usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private static final int USERNAME_LENGTH = 20;

  /** Length máxima permitida para a senha do usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private static final int PASSWORD_LENGTH = 20;

  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** O nome do usuário, é necessário ser unico.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "username", length = USERNAME_LENGTH, nullable = false, unique = true
  )
  private String username;

  /** Email utilizado pelo usuário na hora de fazer login.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "email", nullable = false, unique = true
  )
  private String email;

  /** Senha utilizado pelo usuário na hora de fazer login.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "password", length = PASSWORD_LENGTH, nullable = false
  )
  private String password;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public User() { }

  /** Cria um usuário novo.
  * @param username Nome utilizado no perfil do usuário.
  * @param email Email utilizado para fazer login.
  * @param password Senha utilizada para fazer login.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public User(
    final String username, final String email, final String password
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  /** Getter do atributo id.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @return Um número que refere os dados do usuário salvos no DB.
  */
  public final Long getId() {
    return id;
  }

  /** Getter do atributo email.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @return Retorna o email do usuário.
  */
  public final String getEmail() {
    return email;
  }

  /** Setter do atributo email.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @param userEmail Novo email do usuário
  */
  public final void setEmail(final String userEmail) {
    this.email = userEmail;
  }

  /** Getter do atributo senha.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @return Retorna a senha do usuário.
  */
  public final String getPassword() {
    return password;
  }

  /** Setter do atributo email.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  * @param userPassword Nova senha do usuário
  */
  public final void setPassword(final String userPassword) {
    this.password = userPassword;
  }
}
