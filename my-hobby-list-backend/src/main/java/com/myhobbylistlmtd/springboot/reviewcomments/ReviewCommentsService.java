package com.myhobbylistlmtd.springboot.reviewcomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;
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
}
