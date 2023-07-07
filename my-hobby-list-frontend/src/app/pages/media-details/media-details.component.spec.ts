import { ComponentFixture, TestBed } from '@angular/core/testing';

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

describe('MediaDetailsComponent', () => {
  let component: MediaDetailsComponent;
  let fixture: ComponentFixture<MediaDetailsComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [MediaDetailsComponent, PageNotFoundComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ id: 1 })),
          },
        },
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(MediaDetailsComponent);
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
    const media: IMedia = {
      id: 1,
      image: 'gto-image',
      length: 208,
      volumes: 25,
      name: 'GTO',
      status: 'Completo',
      type: 'Manga',
    };
    const { debugElement } = fixture;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'getMediaById').and.returnValue(of(media));
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(service.getMediaById).toHaveBeenCalled();
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
    const media: IMedia = {
      id: 1,
      image: 'gto-image',
      length: 208,
      volumes: null,
      name: 'GTO',
      status: 'Completo',
      type:'Manga',
    };
    const { debugElement } = fixture;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'getMediaById').and.returnValue(of(media));
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(service.getMediaById).toHaveBeenCalled();
    fixture.detectChanges();

    const mediaVolumes =
      debugElement.nativeElement.querySelector('.media-volumes');
    expect(mediaVolumes).toBeNull();
  });

  it('should render not found component when media isnt found in database', () => {
    const { debugElement } = fixture;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'getMediaById').and.returnValue(
      throwError(
        () => new HttpErrorResponse({ error: { message: 'Error qualquer' } })
      )
    );
    const loadingMessage = debugElement.nativeElement.querySelector(
      '.loading-container p'
    );
    expect(loadingMessage).toBeTruthy();
    component.ngOnInit();
    expect(service.getMediaById).toHaveBeenCalled();
    fixture.detectChanges();

    const notFoundComponent =
      debugElement.nativeElement.querySelector('app-page-not-found');
    expect(notFoundComponent).toBeTruthy();
  });
});
