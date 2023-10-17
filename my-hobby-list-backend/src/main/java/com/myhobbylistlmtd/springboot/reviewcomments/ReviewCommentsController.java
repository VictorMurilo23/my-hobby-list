package com.myhobbylistlmtd.springboot.reviewcomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/review-comments")
@Tag(name = "ReviewComments")
public class ReviewCommentsController {
  /**
   * Service de reviewComments.
   */
  @Autowired
  private ReviewCommentsService reviewCommentsService;
}
