package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.InternalErrorException;
import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;

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

  /**
   * Procura uma media por id, como o próprio nome diz...
   * @param id Um id do tipo do Long.
   * @return Retorna um objeto do tipo media.
   * @throws NotFoundException Ocorre quando o id
   passado não existe no banco de dados.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Media findById(final Long id) throws NotFoundException {
    try {
      Media media = mediaRepo.findById(id).get();
      return media;
    } catch (NoSuchElementException e) {
      throw new NotFoundException("Media não encontrada!");
    }
  }
}
