package com.myhobbylistlmtd.springboot.list.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStatusRepository extends JpaRepository<
ItemStatus, Long
> {
  /**
  * Faz uma busca pelo nome do status.
  * @param status Nome do status buscado.
  * @return A classe MediaStatus ou null,
  caso não tenha sido encontrado o nome do status.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  ItemStatus findByStatus(String status);
}
