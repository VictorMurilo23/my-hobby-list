package com.myhobbylistlmtd.springboot.characters.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharactersRoleRepository
    extends JpaRepository<CharactersRole, Long> {
  /**
   * Busca o papel que pode ser executado por nome.
   * @param roleName Nome do papel
   * @return Um objeto com o nome e id do papel
   */
  CharactersRole findByRoleName(String roleName);
}
