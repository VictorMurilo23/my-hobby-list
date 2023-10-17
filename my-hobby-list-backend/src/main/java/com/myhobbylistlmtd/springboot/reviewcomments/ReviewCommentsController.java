package com.myhobbylistlmtd.springboot.reviewcomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;

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
    ReviewComments test = this.reviewCommentsService.createReview(userId, body);
    return test;
  }
}
