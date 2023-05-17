package com.myhobbylistlmtd.springboot.media.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaStatusRepository extends JpaRepository<
  MediaStatus, Long
> {
    /**
  * Faz uma busca por status.
  * @param status Nome do status buscado.
  * @return A classe MediaStatus ou null,
  caso não tenha sido encontrado o nome do status.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  MediaStatus findByStatus(String status);
}
