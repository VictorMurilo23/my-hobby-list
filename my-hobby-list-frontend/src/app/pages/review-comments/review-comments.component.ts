import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import { Comment, Review, ReviewDetails } from 'src/app/interfaces/IReviews';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ReviewService } from 'src/app/services/review.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-review-comments',
  templateUrl: './review-comments.component.html',
  styleUrls: ['./review-comments.component.css']
})
export class ReviewCommentsComponent implements OnInit {
  private review!: Review;
  private createCommentInfo = { userComment: "", mediaId: 0, usernameFromReview: ""};
  private comments: Comment[] = [];
  readonly tokenUsername: string | null = this.userService.getUsernameFromToken();

  constructor(private reviewService: ReviewService, private activatedRoute: ActivatedRoute, private userService: UserService, private localStorage: LocalStorageService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const mediaId = params.get("mediaId");
          const username = params.get("username");
          if (mediaId !== null && username !== null) {
            this.createCommentInfo.mediaId = Number(mediaId);
            this.createCommentInfo.usernameFromReview = username;
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

  public getUserComment() {
    return this.createCommentInfo.userComment;
  }

  public setUserComment(comment: string) {
    this.createCommentInfo.userComment = comment;
  }

  public sendComment() {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.userService.logout();
      return;
    }
    const { mediaId, userComment, usernameFromReview } = this.createCommentInfo;
    this.reviewService.createComment(token, { commentary: userComment, mediaId, usernameFromReview })
    .subscribe({
      next: (data: Comment) => {
        this.comments.push(data);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
