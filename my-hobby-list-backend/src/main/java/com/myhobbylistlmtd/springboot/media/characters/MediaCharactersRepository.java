package com.myhobbylistlmtd.springboot.media.characters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaCharactersRepository
extends JpaRepository<MediaCharacters, MediaCharactersId> {
  /**
   * Procura todos os personagens de uma media.
   * @param mediaId Id da media.
   * @return Uma lista com todos os personagens daquela media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  // CHECKSTYLE:OFF: MethodNameCheck
  List<MediaCharacters> findAllById_MediaId(Long mediaId);
}
