package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReview;
import com.myhobbylistlmtd.springboot.request.body.RequestEditReview;
import com.myhobbylistlmtd.springboot.response.body.ResponseFindReviews;
import com.myhobbylistlmtd.springboot.reviews.Reviews;
import com.myhobbylistlmtd.springboot.reviews.ReviewsRepository;
import com.myhobbylistlmtd.springboot.reviews.ReviewsService;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
  private ReviewsService reviewService;

  @Mock
  private ReviewsRepository reviewsRepo;

  @Mock
  private UserService userService;

  @Mock
  private MediaService mediaService;

  @BeforeEach
  void setUp() {
    this.reviewService = new ReviewsService(mediaService, userService, reviewsRepo);
  }

  @Test
  public void findReviewWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);

    Reviews review = new Reviews("blablabla", false, media, user);

    when(reviewsRepo.findReviewsByUserIdAndMediaId(anyLong(), anyLong())).thenReturn(review);

    Reviews serviceReturn = this.reviewService.findReview(userId, mediaId);

    verify(reviewsRepo).findReviewsByUserIdAndMediaId(userId, mediaId);
    assertThat(serviceReturn.getContent(), is("blablabla"));
    assertThat(serviceReturn.getEdited(), is(false));
    assertThat(serviceReturn.getMediaId(), is(media));
    assertThat(serviceReturn.getRecommended(), is(false));
    assertThat(serviceReturn.getUserId(), is(user));
  }

  @Test
  public void findReviewThrowsErrorWhenDoesntFindReview() {
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);

    when(reviewsRepo.findReviewsByUserIdAndMediaId(anyLong(), anyLong())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> this.reviewService.findReview(userId, mediaId));

    verify(reviewsRepo).findReviewsByUserIdAndMediaId(userId, mediaId);
  }

  @Test
  public void createReviewWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);
    RequestCreateReview body = new RequestCreateReview();
    body.setContent("df");
    body.setMediaId(mediaId);
    body.setRecommended(false);

    when(reviewsRepo.save(any())).thenReturn(null);
    when(mediaService.findById(mediaId)).thenReturn(media);
    when(userService.findById(userId)).thenReturn(user);
    
    this.reviewService.createReview(body, userId);

    verify(reviewsRepo).save(any());
    verify(mediaService).findById(mediaId);
    verify(userService).findById(userId);
  }

  @Test
  public void editReviewContentWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);
    Reviews review = new Reviews("blablabla", false, media, user);
    RequestEditReview body = new RequestEditReview();
    body.setContent("Content");
    body.setMediaId(mediaId);

    when(reviewsRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
    when(reviewsRepo.findReviewsByUserIdAndMediaId(anyLong(), anyLong())).thenReturn(review);
    
    Reviews serviceReturn = this.reviewService.editReview(body, userId);

    verify(reviewsRepo).save(any());
    assertThat(serviceReturn.getContent(), is("Content"));
    assertThat(serviceReturn.getEdited(), is(true));
    assertThat(serviceReturn.getRecommended(), is(false));
    assertThat(serviceReturn.getMediaId().getName(), is("Teste1"));
    assertThat(serviceReturn.getUserId().getUsername(), is("teste"));
  }

  @Test
  public void editReviewRecommendedWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);
    Reviews review = new Reviews("blablabla", false, media, user);
    RequestEditReview body = new RequestEditReview();
    body.setRecommended(true);
    body.setMediaId(mediaId);

    when(reviewsRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
    when(reviewsRepo.findReviewsByUserIdAndMediaId(anyLong(), anyLong())).thenReturn(review);
    
    Reviews serviceReturn = this.reviewService.editReview(body, userId);

    verify(reviewsRepo).save(any());
    assertThat(serviceReturn.getContent(), is("blablabla"));
    assertThat(serviceReturn.getEdited(), is(true));
    assertThat(serviceReturn.getRecommended(), is(true));
    assertThat(serviceReturn.getMediaId().getName(), is("Teste1"));
    assertThat(serviceReturn.getUserId().getUsername(), is("teste"));
  }

  @Test
  public void editReviewRecommendedAndContentWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);
    Reviews review = new Reviews("blablabla", false, media, user);
    RequestEditReview body = new RequestEditReview();
    body.setRecommended(true);
    body.setContent("d");
    body.setMediaId(mediaId);

    when(reviewsRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
    when(reviewsRepo.findReviewsByUserIdAndMediaId(anyLong(), anyLong())).thenReturn(review);
    
    Reviews serviceReturn = this.reviewService.editReview(body, userId);

    verify(reviewsRepo).save(any());
    assertThat(serviceReturn.getContent(), is("d"));
    assertThat(serviceReturn.getEdited(), is(true));
    assertThat(serviceReturn.getRecommended(), is(true));
    assertThat(serviceReturn.getMediaId().getName(), is("Teste1"));
    assertThat(serviceReturn.getUserId().getUsername(), is("teste"));
  }

  @Test
  public void findReviewsWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Long mediaId = Long.valueOf(4);
    Reviews review = new Reviews("blablabla", false, media, user);

    List<Reviews> items = Arrays.asList(
      review, review, review, review, review
    );
    Page<Reviews> pagedResponse = new PageImpl<Reviews>(items);
    when(reviewsRepo.findAllByMediaId(anyLong(), any())).thenReturn(pagedResponse);
    
    ResponseFindReviews serviceReturn = this.reviewService.findReviews(0, mediaId);

    assertThat(serviceReturn.getTotalPages(), is(1));
    assertThat(serviceReturn.getReviews().size(), is(5));
  }

  @Test
  public void findUserReviewsWithSuccess() {
    Media media = new Media("Teste1");
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    Reviews review = new Reviews("blablabla", false, media, user);

    List<Reviews> items = Arrays.asList(
      review, review, review, review, review
    );
    Page<Reviews> pagedResponse = new PageImpl<Reviews>(items);
    when(reviewsRepo.findAllUserReviews(anyString(), any())).thenReturn(pagedResponse);
    when(userService.findByUsername(anyString())).thenReturn(user);
    
    ResponseFindReviews serviceReturn = this.reviewService.findUserReviews(0, "teste");

    assertThat(serviceReturn.getTotalPages(), is(1));
    assertThat(serviceReturn.getReviews().size(), is(5));
  }
}
