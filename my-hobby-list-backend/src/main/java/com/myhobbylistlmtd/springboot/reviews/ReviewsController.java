package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
import com.myhobbylistlmtd.springboot.response.body.ResponseFindReviews;
import com.myhobbylistlmtd.springboot.response.body.ResponseMessage;
import com.myhobbylistlmtd.springboot.utils.Views;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews")
public class ReviewsController {
  /**
   * Service de reviews.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Autowired
  private ReviewsService reviewsService;

  /**
   * Rota de criar uma review.
   * @param body Corpo da requisição contendo o conteúdo da review,
   o id da media e se o usuário recomenda ou não a media
   * @param userId Id do usuário
   * @return Retorna uma mensagem dizendo que a review foi criada com sucesso.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @PostMapping("/create")
  // TODO alterar o ResponseStatus pra HttpStatus.CREATED e corrigir os testes
  @ResponseStatus(HttpStatus.OK)
  @Operation(
    summary = "Criar uma review",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    }
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "O review criada com sucesso",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseMessage.class)) }
    )
  })
  ResponseMessage createReview(
    @Valid @RequestBody final RequestCreateReview body,
    @RequestAttribute("userId") final Long userId
  ) {
    reviewsService.createReview(body, userId);
    ResponseMessage response = new ResponseMessage();
    response.setMessage("Review criado com sucesso!");
    return response;
  }

  /**
   * Rota de editar uma review.
   * @param body Corpo da requisição contendo o conteúdo da review,
   o id da media e se o usuário recomenda ou não a media.
   Só id da media é obrigatório de ter no body
   * @param userId id do usuário
   * @return Retorna uma mensagem dizendo que a review foi editada com sucesso
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @PatchMapping("/edit")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
    summary = "Editar uma review",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    }
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna a review com as informações editadas",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = Reviews.class)) }
    )
  })
  Reviews editReview(
    @Valid @RequestBody final RequestEditReview body,
    @RequestAttribute("userId") final Long userId
  ) {
    Reviews review = reviewsService.editReview(body, userId);
    return review;
  }


  /**
   * Rota de encontrar reviews utilizando o id de uma media.
   * @param mediaId id da media
   * @param page Número da página
   * @return Retorna de um Json com a lista de reviews e o total de páginas
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Operation(summary = "Encontrar reviews de uma mídia")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com as mídias e o número total de páginas"
      + ". OBS: A numeração das páginas funciona igual a um array, ou seja, "
      + "a primeira página é a de número 0",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseFindReviews.class)) }
    )
  })
  @GetMapping("/find/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView({Views.Review.class})
  ResponseFindReviews findReview(
    final @PathVariable String mediaId,
    final @RequestParam(defaultValue = "0") String page
  ) {
    return reviewsService.findReviews(
      Integer.valueOf(page), Long.valueOf(mediaId)
    );
  }

  /**
   * Rota de encontrar uma review utilizando o id da media e do usuário.
   * @param mediaId id da media
   * @param userId id do usuário vindo do token
   * @return Retorna um objeto contendo as informações da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @GetMapping("/find-user-review/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView({Views.Review.class})
  @Operation(
    summary = "Encontrar uma única review do usuário",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    }
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna as informações de uma única review",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = Reviews.class)) }
    )
  })
  Reviews findReviewByMediaIdAndUserId(
    final @PathVariable String mediaId,
    @RequestAttribute("userId") final Long userId
  ) {
    return reviewsService.findReview(
      Long.valueOf(mediaId), Long.valueOf(mediaId)
    );
  }

  /**
   * Rota de encontrar todas as reviews de um usuário específico.
   * @param username nome do usuário
   * @param page Número da página
   * @return Retorna de um Json com a lista de reviews e o total de páginas
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Operation(summary = "Encontra todas as reviews de um usuário")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com as reviews do usuário "
      + "e o número total de páginas"
      + ". OBS: A numeração das páginas funciona igual a um array, ou seja, "
      + "a primeira página é a de número 0",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseFindReviews.class)) }
    )
  })
  @GetMapping("/find-all-user-reviews/{username}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView({Views.ReviewPage.class})
  ResponseFindReviews findAllUserReviews(
    final @PathVariable String username,
    final @RequestParam(defaultValue = "0") String page
  ) {
    return reviewsService.findUserReviews(Integer.valueOf(page), username);
  }

}
