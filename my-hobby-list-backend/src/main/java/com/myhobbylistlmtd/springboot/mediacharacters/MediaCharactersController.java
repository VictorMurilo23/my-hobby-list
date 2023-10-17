package com.myhobbylistlmtd.springboot.mediacharacters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.response.body.ResponseCharacters;
import com.myhobbylistlmtd.springboot.utils.Views;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/characters")
@Tag(name = "Characters")
public class MediaCharactersController {
  /**
   * Service de MediaCharacters.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Autowired
  private MediaCharactersService mediaCharactersService;

  /**
   * Rota de pegar todos os personagens relacionados a uma media.
   * @param mediaName
   * @return Um objeto contendo todos os personagens
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/{mediaName}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView(Views.Public.class)
  @Operation(summary = "Encontra todos os personagens relacionados a uma media")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com todos os personagens",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseCharacters.class)) }
    )
  })
  ResponseCharacters getCharacters(final @PathVariable String mediaName) {
    List<MediaCharacters> characters = mediaCharactersService
      .getCharacters(mediaName);
    ResponseCharacters body = new ResponseCharacters();
    body.setCharacters(characters);
    return body;
  }
}
