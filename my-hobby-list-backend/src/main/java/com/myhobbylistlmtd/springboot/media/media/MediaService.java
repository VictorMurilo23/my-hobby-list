package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.InternalErrorException;

@Service
public class MediaService {
  /**
  * Repositório de media.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private MediaRepository mediaRepo;

  /**
  * Retorna as 10 medias recém-adicionadas.
  * @return Retorna um token com as informações do usuário
  * @throws InternalErrorException Ocorre quando dar pau em alguma coisa.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public List<Media> getMostRecentMedias() throws InternalErrorException {
    try {
      return mediaRepo.findFirst10ByOrderByInsertionDateDesc();
    } catch (Exception e) {
      throw new InternalErrorException(
        "Erro ao tentar pegar os recém-adicionados"
      );
    }
  }

  /**
   * Busca medias que contém a string passada como parâmetro no nome.
   * @param mediaName Nome a ser buscado.
   * @return Retorna ou uma lista com objetos de Media
   * ou retorna uma lista vazia.
   * @throws InternalErrorException
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public List<Media> findByName(
    final String mediaName
  ) throws InternalErrorException {
    try {
      return mediaRepo.findByNameContainingIgnoreCase(mediaName);
    } catch (Exception e) {
      throw new InternalErrorException(
        "Erro do servidor!"
      );
    }
  }
}
