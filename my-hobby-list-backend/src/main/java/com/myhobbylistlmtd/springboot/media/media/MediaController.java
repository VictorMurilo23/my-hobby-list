package com.myhobbylistlmtd.springboot.media.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.response.body.ResponseRecentMedias;

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
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @GetMapping("/recent-add")
  ResponseRecentMedias validateLogin() {
    List<Media> recentMedias = mediaService.getMostRecentMedias();
    ResponseRecentMedias response = new ResponseRecentMedias();
    response.setMedias(recentMedias);
    return response;
  }
}
