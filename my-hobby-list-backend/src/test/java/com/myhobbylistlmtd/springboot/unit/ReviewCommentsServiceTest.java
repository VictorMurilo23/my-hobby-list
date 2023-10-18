package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewComments;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewCommentsRepository;
import com.myhobbylistlmtd.springboot.reviewcomments.ReviewCommentsService;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsService;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@ExtendWith(MockitoExtension.class)
public class ReviewCommentsServiceTest {
  private ReviewCommentsService reviewCommentsService;

  @Mock
  private ReviewCommentsRepository reviewCommentsRepo;

  @Mock
  private ReviewsService reviewsService;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    this.reviewCommentsService = new ReviewCommentsService(reviewCommentsRepo, reviewsService, userService);
  }

  @Test
  public void createCommentWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);

    Reviews review = new Reviews("blablabla", false, media, user);

    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("testesewad");
    body.setMediaId(mediaId);
    body.setUsernameFromReview("teste");

    when(reviewsService.findReviewByUsernameAndMediaId(anyString(), anyLong())).thenReturn(review);
    when(userService.findById(anyLong())).thenReturn(user);
    when(reviewCommentsRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

    ReviewComments serviceReturn = this.reviewCommentsService.createReview(userId, body);

    verify(reviewsService).findReviewByUsernameAndMediaId(user.getUsername(), mediaId);
    verify(userService).findById(userId);
    verify(reviewCommentsRepo).save(any());

    assertThat(serviceReturn.getEdited(), is(false));
    assertThat(serviceReturn.getCommentary(), is("testesewad"));
    assertThat(serviceReturn.getUsername(), is("teste"));
  }

  @Test
  public void findCommentsWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long mediaId = Long.valueOf(4);

    Reviews review = new Reviews("blablabla", false, media, user);

    List<ReviewComments> list = new ArrayList<ReviewComments>();
    list.add(new ReviewComments(review, user, "Blablabla"));
    list.add(new ReviewComments(
      review,
      new User("Victo", "blablabla@gmail", "123445"),
      "ffffffff"
    ));

    when(reviewCommentsRepo.findReviewsByUsernameAndMediaId(anyString(), anyLong())).thenReturn(list);

    List<ReviewComments> serviceReturn = this.reviewCommentsService
      .findReviewComments("teste", mediaId);

    verify(reviewCommentsRepo).findReviewsByUsernameAndMediaId("teste", mediaId);

    assertThat(serviceReturn.size(), is(2));
    assertThat(serviceReturn.get(0).getCommentary(), is("Blablabla"));
    assertThat(serviceReturn.get(0).getUsername(), is("teste"));
    assertThat(serviceReturn.get(1).getCommentary(), is("ffffffff"));
    assertThat(serviceReturn.get(1).getUsername(), is("Victo"));
  }
}
