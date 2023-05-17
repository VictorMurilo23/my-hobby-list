package com.myhobbylistlmtd.springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
