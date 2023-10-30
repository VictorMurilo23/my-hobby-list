package com.myhobbylistlmtd.springboot.mediacharacters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
  @Query(
    value = "SELECT * FROM media_characters m LEFT "
    + "JOIN character_role r ON m.role_id = r.id WHERE m.media_id = ?1"
    + " ORDER BY m.role_id ASC",
    nativeQuery = true
  )
  List<MediaCharacters> findAllById_MediaId(Long mediaId);
}
