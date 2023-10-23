import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CreateReview, Review } from 'src/app/interfaces/IReviews';
import SendReview from 'src/app/interfaces/SendReview';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ReviewService } from 'src/app/services/review.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css'],
})
export class ReviewsComponent implements OnInit, SendReview {
  private reviews: Review[] = [];
  private mediaId!: number;
  public loading = true;
  private currentPage = 0;
  private totalPages!: number;
  private userReview: Review = { content: "", edited: false, recommended: true, user: { username: "" } };
  public showCreateReviewComponent = true;

  constructor(
    private reviewService: ReviewService,
    private route: ActivatedRoute,
    private localStorage: LocalStorageService,
    private userService: UserService
  ) {
    this.route.parent?.paramMap.subscribe(({ params: { id } }: any) => {
      this.mediaId = id;
    });
  }

  private findReviews() {
    this.loading = true;
    this.reviewService.findReviews(this.mediaId, this.currentPage).subscribe({
      next: (data) => {
        this.totalPages = data.totalPages;
        this.reviews = data.reviews;
      },
    });
    this.loading = false;
  }

  private findUserReview() {
    const token = this.localStorage.getToken();
    if (token === null) {
      return;
    }
    this.reviewService.findUserReview(this.mediaId, token).subscribe({
      next: (review) => {
        this.userReview = review;
        this.showCreateReviewComponent = false;
      },
      error: (_err) => {
        this.showCreateReviewComponent = true;
      }
    });
  }

  ngOnInit(): void {
    this.findReviews();
    this.findUserReview();
  }

  public getPages() {
    const arr = [];
    for (let index = 1; index <= this.totalPages; index += 1) {
      arr.push(index);
    }
    return arr;
  }

  public getReviews(): Review[] {
    return this.reviews;
  }

  public changePage(pageNumber: number) {
    if (pageNumber - 1 === this.currentPage) {
      return;
    }
    this.currentPage = pageNumber - 1;
    this.findReviews();
  }

  public getMediaId() {
    return this.mediaId;
  }

  public getUserReview() {
    return this.userReview;
  }

  sendReview = (review: CreateReview): void => {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.userService.logout();
      return;
    }
    this.reviewService.createReview(review, token).subscribe();
  };
}
