package com.myhobbylistlmtd.springboot.characters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Long> {
  /**
   * Procura um personagem por nome.
   * @param characterName Nome do personagem
   * @return Retorna um objeto Characters com as informações do personagem
   */
  Characters findByName(String characterName);
}
