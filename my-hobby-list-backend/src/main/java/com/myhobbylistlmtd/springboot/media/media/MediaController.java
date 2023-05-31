package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.response.body.ResponseMediasList;

@RestController
@RequestMapping("/media")
public class MediaController {
  /**
  * Serviço do media, responsável pelas regras de negócio.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private MediaService mediaService;

  /**
  * Rota de medias recém-adicionadas, não recebe nenhum parâmetro e
  só retorna uma lista de 10 medias.
  * @return Retorna uma lista com 10 ou menos medias recém adicionadas.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @GetMapping("/recent-add")
  ResponseMediasList findRecentMedias() {
    List<Media> recentMedias = mediaService.getMostRecentMedias();
    ResponseMediasList response = new ResponseMediasList();
    response.setMedias(recentMedias);
    return response;
  }

  /**
  * Rota de medias recém-adicionadas, não recebe nenhum parâmetro e
  só retorna uma lista de 10 medias.
  * @param mediaName O nome passado na url, utilizado na busca de medias.
  * @return Retorna uma lista de objetos Media.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @GetMapping("/search-by-name/{mediaName}")
  ResponseMediasList findMediaByName(final @PathVariable String mediaName) {
    List<Media> recentMedias = mediaService.findByName(mediaName);
    ResponseMediasList response = new ResponseMediasList();
    response.setMedias(recentMedias);
    return response;
  }
}
