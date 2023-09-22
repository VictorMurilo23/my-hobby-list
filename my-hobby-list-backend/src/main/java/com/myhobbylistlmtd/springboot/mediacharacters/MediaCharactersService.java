package com.myhobbylistlmtd.springboot.mediacharacters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;

@Service
public class MediaCharactersService {
  /**
   * Repository de MediaCharacters.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Autowired
  private MediaCharactersRepository mediaCharactersRepo;

  /**
   * Service de media.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Autowired
  private MediaService mediaService;

  /**
   * Constructor utilizado nos testes unitários.
   * @param mediaService Mock de MediaService
   * @param mediaCharactersRepo Mock de MediaCharactersRepository
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public MediaCharactersService(
    final MediaService mediaService,
    final MediaCharactersRepository mediaCharactersRepo
  ) {
    this.mediaCharactersRepo = mediaCharactersRepo;
    this.mediaService = mediaService;
  }

  /**
   * Procura todos os personagens de uma media.
   * @param mediaName Nome da media
   * @return Uma lista com todos os personagens
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public List<MediaCharacters> getCharacters(final String mediaName) {
    Media media = mediaService.findByName(mediaName);
    return mediaCharactersRepo.findAllById_MediaId(media.getId());
  }
}
