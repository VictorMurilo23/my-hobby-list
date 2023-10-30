import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import { CreateReview, UserReviews } from 'src/app/interfaces/IReviews';
import SendReview from 'src/app/interfaces/SendReview';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ReviewService } from 'src/app/services/review.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-reviews-page',
  templateUrl: './user-reviews-page.component.html',
  styleUrls: ['./user-reviews-page.component.css'],
})
export class UserReviewsPageComponent implements OnInit, SendReview {
  constructor(
    private reviewService: ReviewService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private localStorage: LocalStorageService,
    private userService: UserService
  ) {
    this.activatedRoute.queryParams.subscribe(param => { 
      let pageQuery = Number(param['page']);
      if (pageQuery === 0 || isNaN(pageQuery)) {
        pageQuery = 1;
      }
      this.currentPage = pageQuery;
      this.getPageContent(pageQuery - 1);
    });
  }
  private routeUsernameParam!: string;
  private reviews: UserReviews[] = [];
  private currentPage = 1;
  public showNotFound = false;
  private totalPages = 1;
  public editErrorMessage = null;

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(param => { 
      let pageQuery = Number(param['page']);
      if (pageQuery === 0 || isNaN(pageQuery)) {
        pageQuery = 1;
      }
      this.currentPage = pageQuery;
      this.getPageContent(pageQuery - 1);
    }).unsubscribe();
  }

  public getPageContent(pageNum: number): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const username = params.get('username');
          if (username !== null) {
            this.routeUsernameParam = username;
            return this.reviewService.findAllUserReviews(username, pageNum);
          }
          return EMPTY;
        })
      )
      .subscribe({
        next: (data) => {
          this.reviews = data.reviews;
          this.totalPages = data.totalPages;
        },
        error: () => {
          this.showNotFound = true;
        },
      });
  }

  public getTotalPages() {
    return this.totalPages;
  }

  public getTotalPagesArray() {
    const arr = [];
    for (let index = 1; index <= this.totalPages; index += 1) {
      arr.push(index);
    }
    return arr;
  }

  public getCurrentPage() {
    return this.currentPage;
  }

  public setCurrentPage(pageInt: number) {
    this.currentPage = pageInt;
  }

  public getReviews() {
    return this.reviews;
  }

  public showEditBtn() {
    return this.routeUsernameParam === this.userService.getUsernameFromToken();
  }

  public setEditing(review: UserReviews) {
    if (review.editing === undefined) {
      review.editing = true
      return;
    }
    review.editing = !review.editing
  }

  sendReview = (review: CreateReview): void => {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.userService.logout();
      return;
    }
    this.reviewService.editReview(review, token).subscribe({
      next: () => {
        const re = this.getReviews();
        for (let index = 0; index < re.length; index += 1) {
          if (re[index].media.id === review.mediaId) {
            re[index].editing = false;
            break;
          }
        }
      },
      error: (err: HttpErrorResponse) => {
        const errorMessage = err.error.message;
        if (errorMessage === "Token inválido") {
          this.userService.logout();
          return;
        }
        this.editErrorMessage = errorMessage;
      }
    });
  };

  changePage = async (pageNum: number) => {
    this.router.navigate(['reviews', this.routeUsernameParam], { queryParams: { page: pageNum } })
  }
}
