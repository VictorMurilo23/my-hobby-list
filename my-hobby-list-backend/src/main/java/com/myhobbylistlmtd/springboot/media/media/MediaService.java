package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.interfaces.IBasicService;

@Service
public class MediaService implements IBasicService<Media, Long> {
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
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  public List<Media> getMostRecentMedias() {
    return mediaRepo.findFirst10ByOrderByInsertionDateDesc();
  }

  /**
   * Busca medias que contém a string passada como parâmetro no nome.
   * @param mediaName Nome a ser buscado.
   * @return Retorna ou uma lista com objetos de Media
   * ou retorna uma lista vazia.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public List<Media> findAllByName(final String mediaName) {
    return mediaRepo.findAllByNameContainingIgnoreCase(mediaName);
  }

  /**
   * Procura uma única media por nome.
   * @param mediaName Nome da media utilizado na busca
   * @return Retorna um objeto de media
   * @throws NotFoundException Ocorre quando a media não é encontrada
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public Media findByName(final String mediaName) {
    Media media = mediaRepo.findByName(mediaName);
    if (media == null) {
      throw new NotFoundException("Media não encontrada!");
    }
    return media;
  }

  @Override
  public final Media findById(final Long id) throws NotFoundException {
    try {
      Media media = mediaRepo.findById(id).get();
      return media;
    } catch (NoSuchElementException e) {
      throw new NotFoundException("Media não encontrada!");
    }
  }
}
