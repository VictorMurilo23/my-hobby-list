import { Component, Input } from '@angular/core';
import { CreateReview, Review } from 'src/app/interfaces/IReviews';

@Component({
  selector: 'app-create-review[sendReview][mediaId][reviewInfo]',
  templateUrl: './create-review.component.html',
  styleUrls: ['./create-review.component.css']
})
export class CreateReviewComponent {
  @Input() reviewInfo!: Review;
  @Input() mediaId!: number;
  @Input()
  sendReview!: (review: CreateReview) => void; 

  public setReviewContent(content: string) {
    this.reviewInfo.content = content;
  }

  public setReviewRecommended(recommended: boolean) {
    this.reviewInfo.recommended = recommended;
  }

  public getReviewRecommended() {
    return this.reviewInfo.recommended;
  }

  public getReviewContent() {
    return this.reviewInfo.content;
  }
  
  public send() {
    const { content, recommended } = this.reviewInfo;
    this.sendReview({ content, recommended, mediaId: this.mediaId });
  }
}
