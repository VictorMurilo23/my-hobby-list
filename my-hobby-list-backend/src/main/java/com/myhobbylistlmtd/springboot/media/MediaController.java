package com.myhobbylistlmtd.springboot.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;
import com.myhobbylistlmtd.springboot.response.body.ResponseMediasList;
import com.myhobbylistlmtd.springboot.utils.Views;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Media")
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
  @JsonView(Views.MediaCard.class)
  @Operation(summary = "Pega as mídias recém-adicionadas")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200", description = "Pega as 10 mídias recém-adicionadas",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseMediasList.class)) }
    )
  })
  ResponseMediasList findRecentMedias() {
    List<Media> recentMedias = mediaService.getMostRecentMedias();
    ResponseMediasList response = new ResponseMediasList();
    response.setMedias(recentMedias);
    return response;
  }

  /**
  * Rota de procurar medias por nome.
  * @param mediaName O nome passado na url, utilizado na busca de medias.
  * @return Retorna uma lista de objetos Media.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Operation(summary = "Procura mais de uma mídia por nome")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array contendo de mídias "
      + "que incluem a string procurada",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseMediasList.class)) }
    )
  })
  @GetMapping("/search-by-name/{mediaName}")
  @JsonView(Views.MediaCard.class)
  ResponseMediasList findMediaByName(final @PathVariable String mediaName) {
    List<Media> mediasList = mediaService.findAllByName(mediaName);
    ResponseMediasList response = new ResponseMediasList();
    response.setMedias(mediasList);
    return response;
  }

  /**
  * Rota de procurar uma media por id.
  * @param id Id passado na url, utilizado na busca de uma media.
  * @return Um objeto de Media.
  * @throws BadRequestException Ocorre quando é passado um id
  que não pode ser transformado em número
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Operation(summary = "Pega uma única mídia por id")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna as informações da mídia",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseMediasList.class)) }
    )
  })
  @GetMapping("/search-by-id/{id}")
  @JsonView(Views.Public.class)
  Media findMediaById(
    final @PathVariable String id
  ) throws BadRequestException {
    try {
      Long idLong = Long.parseLong(id);
      Media media = mediaService.findById(idLong);
      return media;
    } catch (NumberFormatException e) {
      throw new BadRequestException("Insira um id no formato de número");
    }
  }
}
