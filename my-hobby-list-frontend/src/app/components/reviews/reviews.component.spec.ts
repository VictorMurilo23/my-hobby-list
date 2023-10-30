import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ReviewsComponent } from './reviews.component';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { FindReviews, Review } from 'src/app/interfaces/IReviews';
import { By } from '@angular/platform-browser';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { ReviewService } from 'src/app/services/review.service';
import { CreateReviewComponent } from '../create-review/create-review.component';
import { ReviewCardComponent } from '../review-card/review-card.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { PaginationComponent } from '../pagination/pagination.component';

fdescribe('ReviewsComponent', () => {
  let component: ReviewsComponent;
  let fixture: ComponentFixture<ReviewsComponent>;
  let reviewService: ReviewService;
  let localStorage: LocalStorageService;
  let router: Router;

  const reviewsPageOne: FindReviews = {
    totalPages: 2,
    reviews: [
      { content: "d", edited: false, recommended: false, user: { username: "victo" } },
      { content: "adaeadwawa", edited: false, recommended: true, user: { username: "vito" } }
    ]
  }

  const reviewsPageTwo: FindReviews = {
    totalPages: 2,
    reviews: [
      { content: "awdowahfo", edited: false, recommended: true, user: { username: "Teste" } },
    ]
  }

  let paramsSubject: BehaviorSubject<{
    page: number;
  }>;

  const userReview: Review = {
    content: "Muito bom",
    recommended: true,
    edited: false,
    user: {
      username: "aaaaaaaaaaaaaaaaaaaaa"
    }
  }

  beforeEach(async () => {
    paramsSubject = new BehaviorSubject({
      page: 1
    });
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ReviewsComponent, CreateReviewComponent, ReviewCardComponent, PaginationComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            parent: {
              paramMap: of(convertToParamMap({ id: 1 })),
            },
            queryParams: paramsSubject
          },
        },
      ],
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(ReviewsComponent);
    component = fixture.componentInstance;
    reviewService = fixture.debugElement.injector.get(ReviewService);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    spyOn(reviewService, "findReviews")
      .withArgs(1, 0).and.returnValue(of(reviewsPageOne))
      .withArgs(1, 1).and.returnValue(of(reviewsPageTwo));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component["mediaId"]).toBe(1);
  });

  it('should render reviews', () => {
    const {debugElement} = fixture;
    // fixture.detectChanges();
    // console.log(component.getCurrentPage());
    
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

  it('should change route query on page click', fakeAsync(() => {
    const { debugElement } = fixture;
    fixture.detectChanges();
    const pageLinks = debugElement.queryAll(By.css(".page-link"));

    pageLinks[1].nativeElement.click();
    fixture.detectChanges();
    tick();
    expect(router.url).toBe('/media/1/reviews?page=2');
  }));

  it('should call findReviews every route query change', fakeAsync(() => {
    const { debugElement } = fixture;
    const pageLinks = debugElement.queryAll(By.css(".page-link"));

    pageLinks[1].nativeElement.click();
    paramsSubject.next({ page: 2 })
    tick();
    fixture.detectChanges();
    expect(router.url).toBe('/media/1/reviews?page=2');
    expect(reviewService.findReviews).toHaveBeenCalledTimes(2);

    debugElement.query(By.css(".page-link")).nativeElement.click();
    paramsSubject.next({ page: 1 })
    tick();
    fixture.detectChanges();
    expect(router.url).toBe('/media/1/reviews?page=1');
    expect(reviewService.findReviews).toHaveBeenCalledTimes(3);
    console.log(component.getCurrentPage());
  }));
});
