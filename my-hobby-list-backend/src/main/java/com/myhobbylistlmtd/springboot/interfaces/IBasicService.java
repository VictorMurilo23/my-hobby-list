package com.myhobbylistlmtd.springboot.interfaces;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;

/**
 * @param <T> Tipo da model.
 * @param <S> Tipo do id.
 * @since 1.0
 * @version 1.0
 * @author Victor Murilo
 */
public interface IBasicService<T, S> {
  /**
   * Faz uma busca por Id.
   * @param id Um id do primeiro tipo genérico
   * @return Retorna um objeto do primeiro tipo genérico
   * @throws NotFoundException Ocorre quando a informação lançada não
   é encontrada.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  T findById(S id) throws NotFoundException;
}
