import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewCommentsComponent } from './review-comments.component';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { ReviewService } from 'src/app/services/review.service';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { ReviewDetails } from 'src/app/interfaces/IReviews';
import { of, throwError } from 'rxjs';
import { ReviewCardComponent } from 'src/app/components/review-card/review-card.component';
import { By } from '@angular/platform-browser';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';

describe('ReviewCommentsComponent', () => {
  let component: ReviewCommentsComponent;
  let fixture: ComponentFixture<ReviewCommentsComponent>;
  let reviewService: ReviewService;
  let userService: UserService;
  let localStorage: LocalStorageService;
  let router: Router;

  const reviewAndComments: ReviewDetails = {
    review: {
      content: "Blabalbal",
      edited: false,
      recommended: true,
      user: {
        username: "blablab"
      }
    },
    comments: [
      {
        commentary: "Comentário1",
        commentId: 1,
        edited: false,
        insertionDate: "2023-03-04",
        username: "Victo"
      },
      {
        commentary: "Comentário2",
        commentId: 2,
        edited: false,
        insertionDate: "2023-04-04",
        username: "Teste"
      }
    ]
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ReviewCommentsComponent, PageNotFoundComponent, ReviewCardComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ mediaId: 1, username: "Teste" })),
          },
        }
      ],
    })
    .compileComponents();
    fixture = TestBed.createComponent(ReviewCommentsComponent);
    reviewService = fixture.debugElement.injector.get(ReviewService);
    userService = fixture.debugElement.injector.get(UserService);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    // Esse JSON abaixo é pro array original, reviewAndComments, não ser modificado.
    spyOn(reviewService, 'reviewDetails').and.returnValue(of(JSON.parse(JSON.stringify(reviewAndComments))));
    router = TestBed.inject(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();    
  });

  it('should create', () => {
    expect(component).toBeTruthy();  
  });

  it('shoud render review and all comments', () => {
    const { debugElement } = fixture;
    fixture.detectChanges();
    expect(reviewService.reviewDetails).toHaveBeenCalled();
    const reviewRecommended = debugElement.query(By.css('.review-card-recommended'));
    expect(reviewRecommended).toBeTruthy();
    expect(reviewRecommended.nativeElement.textContent).toBe("Recomendado");

    const reviewUsername = debugElement.query(By.css('.review-card-username'));
    expect(reviewUsername).toBeTruthy();
    expect(reviewUsername.nativeElement.textContent).toBe("blablab");

    const reviewContent = debugElement.query(By.css('.review-card-content'));
    expect(reviewContent).toBeTruthy();
    expect(reviewContent.nativeElement.textContent).toBe("Blabalbal");

    const reviewComments = debugElement.queryAll(By.css('.comment-card'));
    expect(reviewComments.length).toBe(2);

    const firstCommentUsername = reviewComments[0].query(By.css(".comment-username"));
    expect(firstCommentUsername).toBeTruthy();
    expect(firstCommentUsername.nativeElement.textContent).toBe("Victo");
    const firstCommentCommentary = reviewComments[0].query(By.css(".comment-commentary"));
    expect(firstCommentCommentary).toBeTruthy();
    expect(firstCommentCommentary.nativeElement.textContent).toBe("Comentário1");

    const secondCommentUsername = reviewComments[1].query(By.css(".comment-username"));
    expect(secondCommentUsername).toBeTruthy();
    expect(secondCommentUsername.nativeElement.textContent).toBe("Teste");
    const secondCommentCommentary = reviewComments[1].query(By.css(".comment-commentary"));
    expect(secondCommentCommentary).toBeTruthy();
    expect(secondCommentCommentary.nativeElement.textContent).toBe("Comentário2");
  });

  it("should be possible to edit a comment", () => {
    const { debugElement } = fixture;
    spyOn(localStorage, "getToken").and.returnValue("Token")
    spyOn(reviewService, "editComment").and.returnValue(of(reviewAndComments.comments[1]));
    Object.defineProperty(component, 'tokenUsername', { value: "Teste" });
    fixture.detectChanges();
    const editCommentBtn = debugElement.query(By.css('.edit-comment-btn'));
    expect(editCommentBtn).toBeTruthy();
    editCommentBtn.nativeElement.click();               
    fixture.detectChanges();

    const editInput = debugElement.query(By.css('.edit-comment-input'));
    expect(editInput).toBeTruthy();
    editInput.triggerEventHandler('change', { target: { value: "Comentário muito legal :)" } });

    const sendEditedBtn = debugElement.query(By.css(".send-edit-comment-btn"));
    expect(sendEditedBtn).toBeTruthy();
    sendEditedBtn.triggerEventHandler('click');
    fixture.detectChanges();

    const reviewComments = debugElement.queryAll(By.css('.comment-card'));
    const secondCommentUsername = reviewComments[1].query(By.css(".comment-username"));
    expect(secondCommentUsername).toBeTruthy();
    expect(secondCommentUsername.nativeElement.textContent).toBe("Teste");
    const secondCommentCommentary = reviewComments[1].query(By.css(".comment-commentary"));
    expect(secondCommentCommentary).toBeTruthy();
    expect(secondCommentCommentary.nativeElement.textContent).toBe("Comentário muito legal :)");
  });

  it("should be possible to create a new comment if user is logged", () => {
    const { debugElement } = fixture;
    Object.defineProperty(component, 'tokenUsername', { value: "Teste" });
    spyOn(localStorage, "getToken").and.returnValue("Token")
    spyOn(reviewService, "createComment").and.returnValue(of({
      commentId: 3,
      commentary: "Comentário novo",
      edited: false,
      insertionDate: "2023-06-06",
      username: "Teste"
    }));
    fixture.detectChanges();

    const createCommentInput = debugElement.query(By.css('.input-create-comment'));
    expect(createCommentInput).toBeTruthy();
    createCommentInput.triggerEventHandler('change', { target: { value: "Comentário novo" } });

    const createCommentBtn = debugElement.query(By.css(".create-comment-btn"));
    expect(createCommentBtn).toBeTruthy();
    createCommentBtn.triggerEventHandler('click');
    fixture.detectChanges();

    const reviewComments = debugElement.queryAll(By.css('.comment-card'));
    expect(reviewComments.length).toBe(3);

    const commentUsername = reviewComments[2].query(By.css(".comment-username"));
    expect(commentUsername).toBeTruthy();
    expect(commentUsername.nativeElement.textContent).toBe("Teste");
    const commentCommentary = reviewComments[2].query(By.css(".comment-commentary"));
    expect(commentCommentary).toBeTruthy();
    expect(commentCommentary.nativeElement.textContent).toBe("Comentário novo");
  });

  it("should not be possible to create a new comment if user is not logged", () => {
    const { debugElement } = fixture;
    Object.defineProperty(component, 'tokenUsername', { value: null });
    fixture.detectChanges();

    const createCommentInput = debugElement.query(By.css('.input-create-comment'));
    expect(createCommentInput).not.toBeTruthy();
  });

  it('should render notfound page if reviewService.reviewDetails returns an error', () => {
    const { debugElement } = fixture;
    Object.defineProperty(component, 'review', { value: undefined });
    Object.defineProperty(component, 'comments', { value: undefined });
    reviewService.reviewDetails = jasmine.createSpy().and.returnValue(throwError(
      () => new HttpErrorResponse({ error: { message: 'Error qualquer' } })
    ));
    component.ngOnInit();
    fixture.detectChanges();

    const notFoundComponent = debugElement.query(By.directive(PageNotFoundComponent));
    expect(notFoundComponent).toBeTruthy();
  });
});
