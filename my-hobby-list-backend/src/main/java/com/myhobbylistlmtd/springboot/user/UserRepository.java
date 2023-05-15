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
}
