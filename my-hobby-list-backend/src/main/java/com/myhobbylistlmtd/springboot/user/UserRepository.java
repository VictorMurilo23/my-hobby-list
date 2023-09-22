package com.myhobbylistlmtd.springboot.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
  * Faz uma busca por email.
  * @param email Email utilizado na busca.
  * @return A classe User ou null, caso não tenha sido encontrado o email.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  User findByEmail(String email);

  /**
  * Faz uma busca por nome de usuário.
  * @param username Nome de usuário utilizado na busca.
  * @return A classe User ou null,
  caso não tenha sido encontrado o nome de usuário.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  User findByUsername(String username);

  /**
   * Busca usuários utilizando o email e nome de usuário.
   * @param username Nome do usuário
   * @param email Email
   * @return Uma lista de usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Query(
    value = "SELECT * FROM users u WHERE u.username = ?1 OR u.email = ?2",
    nativeQuery = true
  )
  List<User> findByUsernameOrEmail(String username, String email);
}
