package com.myhobbylistlmtd.springboot.media.characters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaCharactersRepository
extends JpaRepository<MediaCharacters, MediaCharactersId> {
}
