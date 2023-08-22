package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
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
   * @param body Corpo da requisição contendo o conteúdo da review, o id da media e se o usuário recomenda ou não a media
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
   * @param body Corpo da requisição contendo o conteúdo da review, o id da media e se o usuário recomenda ou não a media. Só id da media é obrigatório de ter no body
   * @param userId id do usuário
   * @return Retorna uma mensagem dizendo que a review foi editada com sucesso
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @PatchMapping("/edit")
  @ResponseStatus(HttpStatus.OK)
  ResponseMessage editReview(
    @Valid @RequestBody final RequestEditReview body,
    @RequestAttribute("userId") final Long userId
  ) {
    reviewsService.editReview(body, userId);
    ResponseMessage response = new ResponseMessage();
    response.setMessage("Review editada com sucesso");
    return response;
  }
}
