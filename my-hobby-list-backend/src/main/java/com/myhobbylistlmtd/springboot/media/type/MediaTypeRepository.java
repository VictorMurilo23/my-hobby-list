package com.myhobbylistlmtd.springboot.media.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {
  /**
  * Faz uma busca por tipo.
  * @param type Nome do tipo buscado.
  * @return A classe MediaType ou null, caso não tenha sido encontrado o tipo.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  MediaType findByType(String type);
}
