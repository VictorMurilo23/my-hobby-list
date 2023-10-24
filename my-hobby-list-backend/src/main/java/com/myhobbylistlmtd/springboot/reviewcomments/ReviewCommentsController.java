package com.myhobbylistlmtd.springboot.reviewcomments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReviewComment;
import com.myhobbylistlmtd.springboot.response.body.ResponseReviewComments;
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
@RequestMapping("/review-comments")
@Tag(name = "ReviewComments")
public class ReviewCommentsController {
  /**
   * Service de reviewComments.
   */
  @Autowired
  private ReviewCommentsService reviewCommentsService;

  /**
   * Endpoint de criar um comentário em uma review.
   * @param userId Id do usuário que fez o comentário
   * @param body Corpo da requisição com informações
   sobre a review e o conteúdo do comentário
   * @return Retorna um objeto com as informações do comentário
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView({ Views.Comment.class })
  @Operation(
    summary = "Fazer um comentário em uma review",
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
      responseCode = "201",
      description = "O comentário criada com sucesso",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ReviewComments.class)) }
    )
  })
  ReviewComments createComment(
    @RequestAttribute("userId") final Long userId,
    @Valid @RequestBody final RequestCreateReviewComment body
  ) {
    ReviewComments comment = this.reviewCommentsService
      .createReview(userId, body);
    return comment;
  }

  /**
   * .
   * @param username
   * @param mediaId
   * @return d
   */
  @GetMapping("/find/{username}/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView({ Views.Comment.class })
  @Operation(summary = "Pega os comentários de uma review")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com os comentários",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseReviewComments.class)) }
    )
  })
  ResponseReviewComments findComments(
    final @PathVariable String username,
    final @PathVariable String mediaId
  ) {
    try {
      List<ReviewComments> commentsList = this.reviewCommentsService
        .findReviewComments(username, Long.valueOf(mediaId));

      return new ResponseReviewComments(
        commentsList,
        commentsList.get(0).getReviewId()
      );
    } catch (NumberFormatException e) {
      throw new BadRequestException("Insira um mediaId em formato de número");
    }
  }

  /**
   * Endpoint de editar o conteúdo do comentário.
   * @param userId Id do usuário que está querendo editar o comentário
   * @param body Corpo da requisição com mediaId e conteúdo novo
   * @return Retorna o comentário editado
   */
  @PatchMapping("/edit")
  @ResponseStatus(HttpStatus.OK)
  @JsonView({ Views.Comment.class })
  @Operation(
    summary = "Edita um comentário",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    })
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna o comentário editado com sucesso",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ReviewComments.class)) }
    )
  })
  ReviewComments editComment(
    @RequestAttribute("userId") final Long userId,
    @Valid @RequestBody final RequestEditReviewComment body
  ) {
    return this.reviewCommentsService
      .editReviewComment(userId, body);
  }
}
