import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsComponent } from './reviews.component';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { FindReviews, Review } from 'src/app/interfaces/IReviews';
import { By } from '@angular/platform-browser';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of, throwError } from 'rxjs';
import { ReviewService } from 'src/app/services/review.service';
import { CreateReviewComponent } from '../create-review/create-review.component';
import { ReviewCardComponent } from '../review-card/review-card.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';

describe('ReviewsComponent', () => {
  let component: ReviewsComponent;
  let fixture: ComponentFixture<ReviewsComponent>;
  let reviewService: ReviewService;
  let localStorage: LocalStorageService;

  const reviewsPageOne: FindReviews = {
    totalPages: 2,
    reviews: [
      { content: "d", recommended: false, user: { username: "victo" } },
      { content: "adaeadwawa", recommended: true, user: { username: "vito" } }
    ]
  }

  const reviewsPageTwo: FindReviews = {
    totalPages: 2,
    reviews: [
      { content: "awdowahfo", recommended: true, user: { username: "Teste" } },
    ]
  }

  const userReview: Review = {
    content: "Muito bom",
    recommended: true,
    user: {
      username: "aaaaaaaaaaaaaaaaaaaaa"
    }
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ReviewsComponent, CreateReviewComponent, ReviewCardComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            parent: {
              paramMap: of(convertToParamMap({ id: 1 })),
            }
          },
        },
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewsComponent);
    component = fixture.componentInstance;
    reviewService = fixture.debugElement.injector.get(ReviewService);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    spyOn(reviewService, "findReviews").and.returnValue(of(reviewsPageOne));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component["mediaId"]).toBe(1);
  });

  it('should render reviews', () => {
    const {debugElement} = fixture;
    fixture.detectChanges();
    
    const reviewCards = debugElement.queryAll(By.css(".review-card"));
    
    expect(reviewService.findReviews).toHaveBeenCalledWith(1, 0);
    expect(reviewCards.length).toBe(2);

    const firstReviewCard = reviewCards[0];
    const secondReviewCard = reviewCards[1];

    expect(firstReviewCard.children.length).toBe(3);
    expect(firstReviewCard.query(By.css(".review-card-username")).nativeElement.textContent).toBe("victo");
    expect(firstReviewCard.query(By.css(".review-card-content")).nativeElement.textContent).toBe("d");
    expect(firstReviewCard.query(By.css(".review-card-recommended")).nativeElement.textContent).toBe("Não recomendado");

    expect(secondReviewCard.children.length).toBe(3);
    expect(secondReviewCard.query(By.css(".review-card-username")).nativeElement.textContent).toBe("vito");
    expect(secondReviewCard.query(By.css(".review-card-content")).nativeElement.textContent).toBe("adaeadwawa");
    expect(secondReviewCard.query(By.css(".review-card-recommended")).nativeElement.textContent).toBe("Recomendado");
  });

  it('should render change page buttons', () => {
    const {debugElement} = fixture;
    fixture.detectChanges();

    const buttons = debugElement.queryAll(By.css(".change-page-button"));
    expect(buttons).toBeTruthy();
    expect(buttons.length).toBe(2);
  });

  it("should change reviews on page button click", () => {
    const {debugElement} = fixture;
    fixture.detectChanges();

    const buttons = debugElement.queryAll(By.css(".change-page-button"));
    expect(buttons).toBeTruthy();
    expect(buttons.length).toBe(2);
    buttons[1].nativeElement.click();
    fixture.detectChanges();
    expect(reviewService.findReviews).toHaveBeenCalledWith(1, 1);
  });
  
  it("should call findReviews if the current page doesnt change", () => {
    const {debugElement} = fixture;
    fixture.detectChanges();

    const buttons = debugElement.queryAll(By.css(".change-page-button"));
    expect(buttons).toBeTruthy();
    expect(buttons.length).toBe(2);
    buttons[0].nativeElement.click();
    expect(reviewService.findReviews).toHaveBeenCalledTimes(1);
    fixture.detectChanges();
  });

  it("should show user review on success", () => {
    const {debugElement} = fixture;
    spyOn(localStorage, "getToken").and.returnValue("token válido")
    spyOn(reviewService, "findUserReview").and.returnValue(of(userReview));
    component.ngOnInit();
    fixture.detectChanges();

    const userReviewContainer = debugElement.query(By.css(".user-review-card-container"));
    expect(userReviewContainer).toBeTruthy();

    expect(userReviewContainer.query(By.css('.review-card-username')).nativeElement.textContent).toBe("aaaaaaaaaaaaaaaaaaaaa");
    expect(userReviewContainer.query(By.css('.review-card-content')).nativeElement.textContent).toBe("Muito bom");
    expect(userReviewContainer.query(By.css('.review-card-recommended')).nativeElement.textContent).toBe("Recomendado");
  });

  it("should render create-review if findUserReview happens to be a error", () => {
    const {debugElement} = fixture;
    spyOn(localStorage, "getToken").and.returnValue("token válido")
    spyOn(reviewService, "findUserReview").and.returnValue(throwError(
      () =>
        new HttpErrorResponse({ error: { message: 'Error genérico' } })
    ));
    component.ngOnInit();
    fixture.detectChanges();

    const userReviewContainer = debugElement.query(By.css(".user-review-card-container"));
    expect(userReviewContainer).not.toBeTruthy();
  });
});
