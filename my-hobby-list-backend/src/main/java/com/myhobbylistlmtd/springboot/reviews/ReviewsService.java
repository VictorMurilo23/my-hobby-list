package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
import com.myhobbylistlmtd.springboot.response.body.ResponseFindReviews;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@Service
public class ReviewsService {
  /**
   * Repositório de reviews.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Autowired
  private ReviewsRepository reviewsRepo;

  /**
   * userService.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Autowired
  private UserService userService;

  /**
   * mediaService.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Autowired
  private MediaService mediaService;

  /**
   * Encontra uma review no banco de dados.
   * @param userId Id do usuário
   * @param mediaId Id da media
   * @return Retorna um objeto de Reviews
   * @throws NotFoundException Ocorre quando a review não é encontrada
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Reviews findReview(final Long userId, final Long mediaId)
  throws NotFoundException {
    Reviews review = reviewsRepo.findReviewsByUserIdAndMediaId(userId, mediaId);
    if (review == null) {
      throw new NotFoundException("Review não encontrada!");
    }
    return review;
  }

  /**
   * Cria uma review.
   * @param body Corpo da requisição contendo o conteúdo da review,
   id da media e se o usuário recomenda a media
   * @param userId Id do usuário
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void createReview(final RequestCreateReview body, final Long userId) {
    User user = userService.findById(userId);
    Media media = mediaService.findById(body.getMediaId());
    Reviews review = new Reviews(
      body.getContent(), body.getRecommended(), media, user
    );
    reviewsRepo.save(review);
  }

  /**
   * Edita uma review.
   * @param body Corpo da requisição
   * @param userId Id do usuário
   * @return Retorna a review com as edições feitas
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Reviews editReview(final RequestEditReview body, final Long userId) {
    Reviews review = this.findReview(userId, body.getMediaId());
    if (body.getContent() != null) {
      review.setContent(body.getContent());
    }
    if (body.getRecommended() != null) {
      review.setRecommended(body.getRecommended());
    }
    review.setEdited(true);
    return reviewsRepo.save(review);
  }

  /**
   * Encontra reviews.
   * @param page número da página
   * @param mediaId id da media
   * @return Retorna um objeto contendo uma lista de
   reviews e o número total de páginas
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public ResponseFindReviews findReviews(
    final Integer page, final Long mediaId
  ) {
    final int pageSize = 10;
    Pageable test = PageRequest.of(page, pageSize);
    Page<Reviews> d = reviewsRepo.findAllByMediaId(mediaId, test);
    return new ResponseFindReviews(d.getTotalPages(), d.getContent());
  }
}
