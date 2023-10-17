package com.myhobbylistlmtd.springboot.reviewcomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewCommentsService {
  /**
   * Repository de ReviewComments.
   */
  @Autowired
  private ReviewCommentsRepository reviewCommentsRepo;

  /**
   * Constructor utilizado nos testes unitários.
   * @param reviewCommentsRepo Mock de ReviewCommentsRepository
   */
  public ReviewCommentsService(
    final ReviewCommentsRepository reviewCommentsRepo
  ) {
    this.reviewCommentsRepo = reviewCommentsRepo;
  }
}
