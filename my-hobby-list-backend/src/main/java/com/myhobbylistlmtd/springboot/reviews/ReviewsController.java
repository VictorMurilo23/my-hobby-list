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

import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
import com.myhobbylistlmtd.springboot.response.body.ResponseFindReviews;
import com.myhobbylistlmtd.springboot.response.body.ResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
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
  @ResponseStatus(HttpStatus.OK)
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
  @GetMapping("/find/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseFindReviews findReview(
    final @PathVariable String mediaId,
    final @RequestParam(defaultValue = "0") String page
  ) {
    return reviewsService.findReviews(
      Integer.valueOf(page), Long.valueOf(mediaId)
    );
  }
}
