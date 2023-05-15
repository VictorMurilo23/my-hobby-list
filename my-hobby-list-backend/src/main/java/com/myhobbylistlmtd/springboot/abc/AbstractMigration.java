package com.myhobbylistlmtd.springboot.abc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

/**
 * Abstração de uma migration, uma forma de popular o DB.
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
