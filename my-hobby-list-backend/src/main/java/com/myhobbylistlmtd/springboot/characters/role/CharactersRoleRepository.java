package com.myhobbylistlmtd.springboot.characters.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharactersRoleRepository
    extends JpaRepository<CharactersRole, Long> {
}
