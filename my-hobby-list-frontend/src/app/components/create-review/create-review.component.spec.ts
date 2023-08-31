import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReviewComponent } from './create-review.component';
import { HttpClientModule } from '@angular/common/http';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';
import { ReviewService } from 'src/app/services/review.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';

describe('CreateReviewComponent', () => {
  let component: CreateReviewComponent;
  let fixture: ComponentFixture<CreateReviewComponent>;
  let reviewService: ReviewService;
  let localStorage: LocalStorageService;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ CreateReviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateReviewComponent);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    userService = fixture.debugElement.injector.get(UserService);
    reviewService = fixture.debugElement.injector.get(ReviewService);
    component = fixture.componentInstance;
    component.mediaId = 1;
    component.sendReview = jasmine.createSpy().and.returnValue(null);
    component.reviewInfo = { content: "D", recommended: true, user: { username: "Teste" } }
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change review.content value on textarea change event', () => {
    const { debugElement } = fixture;

    const textareaContent = debugElement.query(By.css(".review-content"));
    expect(textareaContent).toBeTruthy();
    textareaContent.triggerEventHandler('change', {
      target: { value: "Comentário muito interessante" },
    });
    fixture.detectChanges();
    expect(component["reviewInfo"]["content"]).toBe("Comentário muito interessante");
    expect(textareaContent.nativeElement.value).toBe("Comentário muito interessante");
  });

  it('should change review.recommended value on recommended buttons', () => {
    const { debugElement } = fixture;

    const recommendedBtns = debugElement.queryAll(By.css(".recommended-btn"));
    expect(recommendedBtns).toBeTruthy();
    recommendedBtns[1].nativeElement.click();
    fixture.detectChanges();
    expect(recommendedBtns[1].attributes["class"]?.includes("activated")).toBeTruthy();
    expect(component["reviewInfo"]["recommended"]).toBe(false);

    recommendedBtns[0].nativeElement.click();
    fixture.detectChanges();
    expect(recommendedBtns[0].attributes["class"]?.includes("activated")).toBeTruthy();
    expect(recommendedBtns[1].attributes["class"]?.includes("activated")).not.toBeTruthy();
    expect(component["reviewInfo"]["recommended"]).toBe(true);
  });

  it('should call reviewService.createReview when user tries to send a review', () => {
    const { debugElement } = fixture;
    spyOn(userService, "logout");
    spyOn(localStorage, "getToken").and.returnValue("fff");

    const textareaContent = debugElement.query(By.css(".review-content"));
    expect(textareaContent).toBeTruthy();
    textareaContent.triggerEventHandler('change', {
      target: { value: "Comentário muito interessante" },
    });

    const recommendedBtns = debugElement.queryAll(By.css(".recommended-btn"));
    expect(recommendedBtns).toBeTruthy();
    recommendedBtns[1].nativeElement.click();
    fixture.detectChanges();

    const sendReviewBtn = debugElement.query(By.css(".send-review-btn"));
    sendReviewBtn.nativeElement.click();

    expect(component.sendReview).toHaveBeenCalled();
  });
});
