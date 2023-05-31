package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
  /**
  * Busca os últimos 10 medias inseridos.
  * @return Os 10 medias recentemente inseridos no banco de dados.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  List<Media> findFirst10ByOrderByInsertionDateDesc();

  /**
  * Busca as medias que incluem a string passado como parâmetro em seu nome.
  * @param name Nome a ser buscado, pode ser passado em caixa alta ou baixa.
  * @return Uma lista de medias que se encaixam no critério de busca.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  List<Media> findByNameContainingIgnoreCase(String name);
}
