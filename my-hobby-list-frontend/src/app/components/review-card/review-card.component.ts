import { Component, Input } from '@angular/core';
import { Review } from 'src/app/interfaces/IReviews';

@Component({
  selector: 'app-review-card',
  templateUrl: './review-card.component.html',
  styleUrls: ['./review-card.component.css']
})
export class ReviewCardComponent {
  @Input() reviewInfo!: Review;
}
