import { Component, Input } from '@angular/core';
import { CreateReview } from 'src/app/interfaces/IReviews';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ReviewService } from 'src/app/services/review.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-review',
  templateUrl: './create-review.component.html',
  styleUrls: ['./create-review.component.css']
})
export class CreateReviewComponent {
  @Input() mediaId!: number;
  private review: CreateReview;

  constructor(private reviewService: ReviewService, private localStorage: LocalStorageService, private userService: UserService) {
    this.review = { content: "", mediaId: this.mediaId, recommended: true };
  }

  public setReviewContent(content: string) {
    this.review.content = content;
  }

  public setReviewRecommended(recommended: boolean) {
    this.review.recommended = recommended;
  }

  public getReviewRecommended() {
    return this.review.recommended;
  }

  public getReviewContent() {
    return this.review.content;
  }

  public sendReview() {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.userService.logout();
      return;
    }
    this.review.mediaId = this.mediaId;
    this.reviewService.createReview(this.review, token).subscribe();
  }
}
