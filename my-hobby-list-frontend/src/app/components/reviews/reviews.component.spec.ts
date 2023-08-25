import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsComponent } from './reviews.component';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FindReviews } from 'src/app/interfaces/IReviews';
import { By } from '@angular/platform-browser';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { ReviewService } from 'src/app/services/review.service';

describe('ReviewsComponent', () => {
  let component: ReviewsComponent;
  let fixture: ComponentFixture<ReviewsComponent>;
  let reviewService: ReviewService;

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

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ReviewsComponent ],
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
    expect(firstReviewCard.query(By.css(".review-card-recommended")).nativeElement.textContent).toBe("false");

    expect(secondReviewCard.children.length).toBe(3);
    expect(secondReviewCard.query(By.css(".review-card-username")).nativeElement.textContent).toBe("vito");
    expect(secondReviewCard.query(By.css(".review-card-content")).nativeElement.textContent).toBe("adaeadwawa");
    expect(secondReviewCard.query(By.css(".review-card-recommended")).nativeElement.textContent).toBe("true");
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
});
