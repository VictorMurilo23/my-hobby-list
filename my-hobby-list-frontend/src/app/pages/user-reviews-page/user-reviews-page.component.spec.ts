import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserReviewsPageComponent } from './user-reviews-page.component';
import { ReviewCardComponent } from 'src/app/components/review-card/review-card.component';
import { UserReviews } from 'src/app/interfaces/IReviews';
import { CreateReviewComponent } from 'src/app/components/create-review/create-review.component';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { UserService } from 'src/app/services/user.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';

describe('UserReviewsPageComponent', () => {
  let component: UserReviewsPageComponent;
  let fixture: ComponentFixture<UserReviewsPageComponent>;
  let reviewService: ReviewService;
  let localStorage: LocalStorageService;
  let userService: UserService;

  const reviews: UserReviews[] = [
    {
      content: 'df',
      recommended: true,
      user: { username: 'Vic' },
      media: { id: 1, image: '/d', name: 'GGGGGGGG' },
    },
    {
      content: 'ffff',
      recommended: true,
      user: { username: 'Vic' },
      media: { id: 2, image: '/f', name: 'EEEEEEE' },
    },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [
        UserReviewsPageComponent,
        ReviewCardComponent,
        CreateReviewComponent,
        PageNotFoundComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ username: 'Teste' })),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(UserReviewsPageComponent);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    reviewService = fixture.debugElement.injector.get(ReviewService);
    userService = fixture.debugElement.injector.get(UserService);
    spyOn(localStorage, 'getToken').and.returnValue('d');
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render not found page when user isnt found', () => {
    const { debugElement } = fixture;
    reviewService.findAllUserReviews = jasmine.createSpy().and.returnValue(
      throwError(() => {
        new HttpErrorResponse({ error: { message: 'Usuário não encontrado' } });
      })
    );
    component.ngOnInit();
    fixture.detectChanges();
    const notFoundComponent = debugElement.query(By.css('app-page-not-found'));
    expect(notFoundComponent).toBeTruthy();
  });

  it('should render user reviews', () => {
    const { debugElement } = fixture;
    spyOn(reviewService, 'findAllUserReviews').and.returnValue(
      of({ totalPages: 1, reviews })
    );
    component.ngOnInit();
    fixture.detectChanges();
    const reviewCards = debugElement.queryAll(By.css('.review-container'));

    expect(reviewCards.length).toBe(2);
  });

  it('should be possible to edit a review', () => {
    const { debugElement } = fixture;
    spyOn(reviewService, 'editReview').and.returnValue(
      of({ teste: 'dfawfaff' })
    );
    spyOn(reviewService, 'findAllUserReviews').and.returnValue(
      of({ totalPages: 1, reviews })
    );
    spyOn(userService, 'getUsernameFromToken').and.returnValue('Teste');
    component.ngOnInit();
    fixture.detectChanges();

    const firstReview = debugElement.query(By.css('.review-container'));
    const content = firstReview.query(By.css('.review-card-content'))
      .nativeElement.textContent;
    const recommended = firstReview.query(By.css('.review-card-recommended'))
      .nativeElement.textContent;
    expect(firstReview).toBeTruthy();
    expect(content).toBe('df');
    expect(recommended).toBe('Recomendado');
    const editBtn = firstReview.query(By.css('.edit-review-button'));
    expect(editBtn).toBeTruthy();
    editBtn.nativeElement.click();
    fixture.detectChanges();
    expect(firstReview.query(By.css('app-create-review'))).toBeTruthy();
    fixture.detectChanges();
    const reviewContent = firstReview.query(By.css('.review-content'));
    expect(reviewContent).toBeTruthy();
    reviewContent.triggerEventHandler('change', {
      target: { value: 'Muito bom' },
    });
    fixture.detectChanges();
    const sendBtn = firstReview.query(By.css('.send-review-btn'));
    expect(sendBtn).toBeTruthy();
    sendBtn.nativeElement.click();
    fixture.detectChanges();

    expect(
      firstReview.query(By.css('.review-card-content')).nativeElement
        .textContent
    ).toBe('Muito bom');
    expect(
      firstReview.query(By.css('.review-card-recommended')).nativeElement
        .textContent
    ).toBe('Recomendado');
    expect(firstReview.query(By.css('app-create-review'))).not.toBeTruthy();
  });

  it('should not render edit button if username in token is different from url username', () => {
    const { debugElement } = fixture;
    spyOn(reviewService, 'findAllUserReviews').and.returnValue(
      of({ totalPages: 1, reviews })
    );
    userService.getUsernameFromToken = jasmine
      .createSpy()
      .and.returnValue('Vdwiaodghaiudifg8iuf');
    component.ngOnInit();
    fixture.detectChanges();

    const firstReview = debugElement.query(By.css('.review-container'));
    expect(firstReview).toBeTruthy();
    const editBtn = firstReview.query(By.css('.edit-review-button'));
    expect(editBtn).not.toBeTruthy();
  });

  it('should call logout when sendBtn is clicked and token is null', () => {
    const { debugElement } = fixture;
    spyOn(reviewService, 'findAllUserReviews').and.returnValue(
      of({ totalPages: 1, reviews })
    );
    spyOn(userService, 'getUsernameFromToken').and.returnValue('Teste');
    spyOn(reviewService, 'editReview').and.returnValue(
      of({ teste: 'dfawfaff' })
    );
    spyOn(userService, 'logout').and.returnValue();
    component.ngOnInit();
    fixture.detectChanges();

    const firstReview = debugElement.query(By.css('.review-container'));
    expect(firstReview).toBeTruthy();
    const editBtn = firstReview.query(By.css('.edit-review-button'));
    expect(editBtn).toBeTruthy();
    editBtn.nativeElement.click();
    fixture.detectChanges();
    const editComponent = firstReview.query(By.css('app-create-review'));
    expect(editComponent).toBeTruthy();

    fixture.detectChanges();
    localStorage.getToken = jasmine.createSpy().and.returnValue(null);
    const sendBtn = editComponent.query(By.css('.send-review-btn'));
    expect(sendBtn).toBeTruthy();
    sendBtn.nativeElement.click();
    fixture.detectChanges();
    expect(userService.logout).toHaveBeenCalled();
  });

  it('should call logout when editReview returns a HttpError with "token inválido" message', () => {
    const { debugElement } = fixture;
    spyOn(reviewService, 'findAllUserReviews').and.returnValue(
      of({ totalPages: 1, reviews })
    );
    spyOn(reviewService, 'editReview').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            error: { message: 'Token inválido' },
          })
      )
    );
    spyOn(userService, 'logout').and.returnValue();
    spyOn(userService, 'getUsernameFromToken').and.returnValue('Teste');
    component.ngOnInit();
    fixture.detectChanges();

    const firstReview = debugElement.query(By.css('.review-container'));
    expect(firstReview).toBeTruthy();
    const editBtn = firstReview.query(By.css('.edit-review-button'));
    expect(editBtn).toBeTruthy();
    editBtn.nativeElement.click();
    fixture.detectChanges();
    const editComponent = firstReview.query(By.css('app-create-review'));
    expect(editComponent).toBeTruthy();

    fixture.detectChanges();
    const sendBtn = editComponent.query(By.css('.send-review-btn'));
    expect(sendBtn).toBeTruthy();
    sendBtn.nativeElement.click();
    fixture.detectChanges();
    expect(userService.logout).toHaveBeenCalled();
  });
});
