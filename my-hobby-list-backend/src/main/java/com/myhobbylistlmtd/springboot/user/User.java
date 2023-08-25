package com.myhobbylistlmtd.springboot.user;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

  /** Length máxima permitida para a descrição de perfil do usuário.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
  */
  private static final int DESCRIPTION_LENGTH = 160;

  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonView(Views.Internal.class)
  private Long id;

  /** O nome do usuário, é necessário ser unico.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "username", length = USERNAME_LENGTH, nullable = false
  )
  @JsonView({Views.Public.class, Views.Review.class})
  private String username;

  /** Email utilizado pelo usuário na hora de fazer login.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "email", nullable = false
  )
  @JsonView(Views.Internal.class)
  private String email;

  /** Senha utilizado pelo usuário na hora de fazer login.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "password", length = PASSWORD_LENGTH, nullable = false
  )
  @JsonIgnore
  private String password;

  /** Url da foto de perfil utilizada pelo usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "profile_image", nullable = false
  )
  @JsonView(Views.Public.class)
  private String profileImage = "images/profile/default.jpg";

  /** Data em que o usuário se registrou.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "joined_at", nullable = false
  )
  @JsonView(Views.Public.class)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime joinedAt = LocalDateTime.now();

  /** Descrição do perfil do usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(
    name = "user_description", nullable = false, length = DESCRIPTION_LENGTH
  )
  @JsonView(Views.Public.class)
  private String userDescription = "";

  /** Relacionamento com reviews.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @OneToMany(mappedBy = "userId")
  @JsonIgnore
  private Set<Reviews> reviews;

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

  /**
   * Getter do atributo username.
   * @return Retorna o username
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter de joinedAt.
   * @return Retorna a data em que o usuário se registrou
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public LocalDateTime getJoinedAt() {
    return joinedAt;
  }

  /**
   * Getter de profileImage.
   * @return Retorna a url da imagem de perfil do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getProfileImage() {
    return profileImage;
  }

  /**
   * Setter de profileImage.
   * @param profileImageUrl Url da imagem de perfil
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setProfileImage(final String profileImageUrl) {
    this.profileImage = profileImageUrl;
  }

  /**
   * Getter de userDescription.
   * @return Descrição do perfil do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String getUserDescription() {
    return userDescription;
  }

  /**
   * Setter de userDescription.
   * @param userDescriptionStr String com a descrição do perfil do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public void setUserDescription(final String userDescriptionStr) {
      this.userDescription = userDescriptionStr;
  }
}
