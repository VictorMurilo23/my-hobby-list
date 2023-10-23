import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewCommentsComponent } from './review-comments.component';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { HttpClientModule } from '@angular/common/http';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';

describe('ReviewCommentsComponent', () => {
  let component: ReviewCommentsComponent;
  let fixture: ComponentFixture<ReviewCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ReviewCommentsComponent, PageNotFoundComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
