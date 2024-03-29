import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { MediaDetailsComponent } from './media-details.component';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of, throwError } from 'rxjs';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { MediaService } from 'src/app/services/media.service';
import IMedia from 'src/app/interfaces/IMedia';
import { environment } from 'src/environments/environment';
import { By } from '@angular/platform-browser';
import { CharactersComponent } from 'src/app/components/characters/characters.component';
import { CharacterService } from 'src/app/services/character.service';
import ICharacters from 'src/app/interfaces/ICharacters';
import { ReviewsComponent } from 'src/app/components/reviews/reviews.component';
import { InsertComponent } from '../insert/insert.component';

describe('MediaDetailsComponent', () => {
  let component: MediaDetailsComponent;
  let fixture: ComponentFixture<MediaDetailsComponent>;
  let router: Router;
  let mediaService: MediaService;

  const media: IMedia = {
    id: 1,
    image: 'gto-image',
    length: 208,
    volumes: 25,
    name: 'GTO',
    status: 'Completo',
    type: 'Manga',
  };

  const mediaWithoutVolumes: IMedia = {
    ...media,
    volumes: null
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [MediaDetailsComponent, PageNotFoundComponent, CharactersComponent, InsertComponent, ReviewsComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ id: 1 })),
          },
        }
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(MediaDetailsComponent);
    mediaService = fixture.debugElement.injector.get(MediaService);
    component = fixture.componentInstance;
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render loading when getting media', () => {
    const { debugElement } = fixture;
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    expect(loadingMessage.textContent).toBe('loading...');
  });

  it('should load media with sucess', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(media));
    spyOn(mediaService, "setMediaName").and.callFake((name) => "");
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(mediaService.getMediaById).toHaveBeenCalled();
    expect(mediaService.setMediaName).toHaveBeenCalled();
    fixture.detectChanges();

    const mediaTitle = debugElement.nativeElement.querySelector('.media-title');
    expect(mediaTitle).toBeTruthy();
    expect(mediaTitle.textContent).toBe('GTO');

    const mediaDuration =
      debugElement.nativeElement.querySelector('.media-duration');
    expect(mediaDuration).toBeTruthy();
    expect(mediaDuration.textContent).toBe('Duração: 208');

    const mediaVolumes =
      debugElement.nativeElement.querySelector('.media-volumes');
    expect(mediaVolumes).toBeTruthy();
    expect(mediaVolumes.textContent).toBe('Volumes: 25');

    const mediaType = debugElement.nativeElement.querySelector('.media-type');
    expect(mediaType).toBeTruthy();
    expect(mediaType.textContent).toBe('Tipo: Manga');

    const mediaStatus =
      debugElement.nativeElement.querySelector('.media-status');
    expect(mediaStatus).toBeTruthy();
    expect(mediaStatus.textContent).toBe('Status: Completo');

    const mediaImage = debugElement.nativeElement.querySelector(
      '.media-image-container img'
    );
    expect(mediaImage).toBeTruthy();
    expect(mediaImage.getAttribute('src')).toBe(
      `${environment.apiUrl}/${media.image}`
    );
  });

  it('should not render volumes field when media doesnt have volumes', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithoutVolumes));
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(mediaService.getMediaById).toHaveBeenCalled();
    fixture.detectChanges();

    const mediaVolumes =
      debugElement.nativeElement.querySelector('.media-volumes');
    expect(mediaVolumes).toBeNull();
  });

  it('should render not found component when media isnt found in database', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(
      throwError(
        () => new HttpErrorResponse({ error: { message: 'Error qualquer' } })
      )
    );
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(mediaService.getMediaById).toHaveBeenCalled();
    fixture.detectChanges();

    const notFoundComponent =
      debugElement.nativeElement.querySelector('app-page-not-found');
    expect(notFoundComponent).toBeTruthy();
  });

  it('should redirect to insert page when \"Adicionar a lista\" button is clicked', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(media));
    component.ngOnInit();

    fixture.detectChanges();
    const redirectBtn = debugElement.query(By.css(".redirect-to-insert-page-button"));
    expect(redirectBtn).toBeTruthy();
    redirectBtn.nativeElement.click();
    tick();

    expect(router.url).toBe(`/media/insert/${media.id}`);
  }));

  it('should redirect to characters nested route on button click', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(media));
    component.ngOnInit();
    fixture.detectChanges();

    const redirectToCharacters = debugElement.query(By.css(".show-characters-button"));
    expect(redirectToCharacters).toBeTruthy();
    redirectToCharacters.nativeElement.click();
    expect(redirectToCharacters.nativeElement.getAttribute("ng-reflect-router-link")).toBe("./characters");
  });

  it('should redirect to reviews nested route on button click', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(media));
    
    component.ngOnInit();
    fixture.detectChanges();
    const redirectToReviews = debugElement.query(By.css(".show-reviews-button"));
    expect(redirectToReviews).toBeTruthy();
    expect(redirectToReviews.nativeElement.getAttribute("ng-reflect-router-link")).toBe("./reviews");
  });
});
