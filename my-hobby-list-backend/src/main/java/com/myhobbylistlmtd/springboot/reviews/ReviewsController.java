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
   */
  @Autowired
  private ReviewsService reviewsService;

  // TODO Fazer uma documentação decente pras rotas abaixo
  /**
   * blabla.
   * @param body
   * @param userId
   * @return f.
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
   * f.
   * @param body f
   * @param userId f
   * @return f
   */
  @PatchMapping("/edit")
  @ResponseStatus(HttpStatus.OK)
  ResponseMessage editReview(
    @Valid @RequestBody final RequestEditReview body,
    @RequestAttribute("userId") final Long userId
  ) {
    reviewsService.editReview(body, userId);
    ResponseMessage response = new ResponseMessage();
    System.out.print(body.getContent());
    return response;
  }
}
