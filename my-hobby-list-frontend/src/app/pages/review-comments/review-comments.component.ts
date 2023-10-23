import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import { Comment, Review, ReviewDetails } from 'src/app/interfaces/IReviews';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-review-comments',
  templateUrl: './review-comments.component.html',
  styleUrls: ['./review-comments.component.css']
})
export class ReviewCommentsComponent implements OnInit {
  private review!: Review;
  private comments: Comment[] = [];

  constructor(private reviewService: ReviewService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const mediaId = params.get("mediaId");
          const username = params.get("username");
          if (mediaId !== null && username !== null) {
            return this.reviewService.reviewDetails(username, mediaId);
          }
          return EMPTY;
        })
      )
      .subscribe({
        next: (data: ReviewDetails) => {
          this.review = data.review;
          this.comments.push(...data.comments);
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  public getReview() {
    return this.review;
  }

  public getComments() {
    return this.comments;
  }
}
