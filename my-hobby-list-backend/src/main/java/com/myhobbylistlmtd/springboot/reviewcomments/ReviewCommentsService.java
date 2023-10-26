package com.myhobbylistlmtd.springboot.reviewcomments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReviewComment;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsService;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@Service
public class ReviewCommentsService {
  /**
   * Repository de ReviewComments.
   */
  @Autowired
  private ReviewCommentsRepository reviewCommentsRepo;

  /**
   * Service de Reviews.
   */
  @Autowired
  private ReviewsService reviewsService;

  /**
   * Service de User.
   */
  @Autowired
  private UserService userService;

  /**
   * Constructor utilizado nos testes unitários.
   * @param reviewCommentsRepo Mock de ReviewCommentsRepository
   * @param reviewsService Mock de ReviewsService
   * @param userService Mock de UserService
   */
  public ReviewCommentsService(
    final ReviewCommentsRepository reviewCommentsRepo,
    final ReviewsService reviewsService,
    final UserService userService
  ) {
    this.reviewCommentsRepo = reviewCommentsRepo;
    this.reviewsService = reviewsService;
    this.userService = userService;
  }

  /**
   * Cria um comentário em uma review.
   * @param userId Id do usuário que está fazendo o comentário
   * @param body Corpo da requisição com mediaId,
   * nome do usuário que fez a review e conteúdo do comentário.
   * @return Retorna um objeto contendo as informações do comentário.
   */
  public ReviewComments createReview(
    final Long userId, final RequestCreateReviewComment body
  ) {
    Reviews review = reviewsService.findReviewByUsernameAndMediaId(
      body.getUsernameFromReview(), body.getMediaId()
    );
    User user = userService.findById(userId);
    ReviewComments comment = new ReviewComments(
      review,
      user,
      body.getCommentary()
    );
    return reviewCommentsRepo.save(comment);
  }

  /**
   * Encontra todos os comentários de uma review.
   * @param username Nome do usuário que fez a review
   * @param mediaId Id da media
   * @return Lista com todos os comentários
   */
  public List<ReviewComments> findReviewComments(
    final String username,
    final Long mediaId
  ) {
    return reviewCommentsRepo
      .findReviewsByUsernameAndMediaId(username, mediaId);
  }

  /**
   * Encontra um único comentário de uma review.
   * @param userId Id do usuário que fez a review
   * @param mediaId Id da media
   * @param commentId Id do comentário
   * @return Comentário feito pelo usuário
   * @throws NotFoundException Ocorre quando o comentário não é encontrado
   */
  public ReviewComments findComment(
    final Long userId,
    final Long mediaId,
    final Long commentId
  ) throws NotFoundException {
    ReviewComments comment = reviewCommentsRepo
      .findCommentByUserIdAndMediaIdAndCommentId(userId, mediaId, commentId);
    if (comment == null) {
      throw new NotFoundException("Comentário não encontrado");
    }
    return comment;
  }

  /**
   * Edita o conteúdo de um comentário.
   * @param userId Id do usuário que fez o comentário
   * @param body Body com o mediaId e o novo conteúdo do comentário
   * @return O comentário atualizado
   */
  public ReviewComments editReviewComment(
    final Long userId,
    final RequestEditReviewComment body
  ) {
    ReviewComments comment = this.findComment(
      userId,
      body.getMediaId(),
      body.getCommentId()
    );
    comment.setCommentary(body.getCommentary());
    comment.setEdited(true);
    return reviewCommentsRepo.save(comment);
  }
}
