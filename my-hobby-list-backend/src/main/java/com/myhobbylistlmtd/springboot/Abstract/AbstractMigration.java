package com.myhobbylistlmtd.springboot.Abstract;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
// import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstração de uma migration.
 * @param <T> Deve receber um repository que irá ser utilizado para popular
 * o banco de dados.
 */
public abstract class AbstractMigration<T> {
  /**
   * Método utilzado para popular o banco de dados com informações.
   * @param repository
   * @return void
   */
  @Bean
  public abstract CommandLineRunner initDatabase(T repository);
}
