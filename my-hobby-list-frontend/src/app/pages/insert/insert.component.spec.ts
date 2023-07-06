import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';

import { InsertComponent } from './insert.component';
import routes from 'src/app/app.routes';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of, throwError } from 'rxjs';
import { MediaService } from 'src/app/services/media.service';
import IMedia from 'src/app/interfaces/IMedia';
import { By } from '@angular/platform-browser';
import { statusNameArray } from 'src/assets/statusNameArray';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ListService } from 'src/app/services/list.service';

describe('InsertComponent', () => {
  let component: InsertComponent;
  let fixture: ComponentFixture<InsertComponent>;
  let router: Router;
  let mediaService: MediaService;
  let localStorageService: LocalStorageService;
  let listService: ListService;

  const mediaWithoutVolumes: IMedia = {
    id: 1,
    image: 'gto-image',
    length: 208,
    volumes: null,
    name: 'GTO',
    status: 'Completo',
    type: 'Manga',
  };

  const mediaWithVolumes: IMedia = {
    ...mediaWithoutVolumes,
    volumes: 28,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [InsertComponent, PageNotFoundComponent],
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
    fixture = TestBed.createComponent(InsertComponent);
    component = fixture.componentInstance;
    mediaService = fixture.debugElement.injector.get(MediaService);
    localStorageService = fixture.debugElement.injector.get(LocalStorageService);
    listService = fixture.debugElement.injector.get(ListService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getMediaById on init', () => {
    spyOn(mediaService, 'getMediaById').and.returnValue(
      of(mediaWithoutVolumes)
    );
    expect(mediaService.getMediaById).not.toHaveBeenCalled();
    component.ngOnInit();
    fixture.detectChanges();
    expect(mediaService.getMediaById).toHaveBeenCalled();
  });

  it('should render not found component when doesnt found media', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({ error: { message: 'Media não encontrada' } })
      )
    );
    component.ngOnInit();
    expect(mediaService.getMediaById).toHaveBeenCalled();
    fixture.detectChanges();

    const notFoundElement = debugElement.query(By.css('app-page-not-found'));
    expect(notFoundElement).toBeTruthy();
  });

  it('should render loading... before getting info', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(
      of(mediaWithoutVolumes)
    );

    expect(mediaService.getMediaById).not.toHaveBeenCalled();
    const loadingElement = debugElement.query(By.css('p')).nativeElement;
    expect(loadingElement).toBeTruthy();
    expect(loadingElement.textContent).toBe('loading...');
  });

  it('should render media info with volumes', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    component.ngOnInit();
    fixture.detectChanges();
    const mediaName = debugElement.nativeElement.querySelector('.media-name');
    expect(mediaName).toBeTruthy();
    expect(mediaName.textContent).toBe('GTO');

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container .length')
    );
    expect(durationInfoContainers.length).toBe(2);
    expect(durationInfoContainers[0].nativeElement.textContent).toBe(' / 208');
    expect(durationInfoContainers[1].nativeElement.textContent).toBe(' / 28');
  });

  it('should render media info without volumes', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(
      of(mediaWithoutVolumes)
    );
    component.ngOnInit();
    fixture.detectChanges();

    const mediaName = debugElement.nativeElement.querySelector('.media-name');
    expect(mediaName).toBeTruthy();
    expect(mediaName.textContent).toBe('GTO');

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container .length')
    );
    expect(durationInfoContainers.length).toBe(1);
    expect(durationInfoContainers[0].nativeElement.textContent).toBe(' / 208');
  });

  it('should render status options based on statusNameArray', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    component.ngOnInit();
    fixture.detectChanges();

    const statusOptions = debugElement.queryAll(
      By.css('.status-container select option')
    );
    expect(statusOptions.length).toBe(statusNameArray.length);

    for (let index = 0; index < statusOptions.length; index += 1) {
      const element = statusOptions[index].nativeElement;
      expect(element.textContent).toBe(statusNameArray[index]);
    }
  });

  it('should call changeValue on field change', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    spyOn(component, 'changeValue');
    component.ngOnInit();
    fixture.detectChanges();

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );

    const lengthElement = durationInfoContainers[0];
    expect(lengthElement).toBeTruthy();
    lengthElement.triggerEventHandler('change', {});

    const volumesElement = durationInfoContainers[1];
    expect(volumesElement).toBeTruthy();
    volumesElement.triggerEventHandler('change', {});

    const scoreElement = debugElement.query(By.css('.score-container select'));
    expect(scoreElement).toBeTruthy();
    scoreElement.triggerEventHandler('change', {});

    const statusElement = debugElement.query(
      By.css('.status-container select')
    );
    expect(statusElement).toBeTruthy();
    statusElement.triggerEventHandler('change', {});

    const notesElement = debugElement.query(
      By.css('.notes-container textarea')
    );
    expect(notesElement).toBeTruthy();
    notesElement.triggerEventHandler('change', {});

    expect(component.changeValue).toHaveBeenCalledTimes(5);
  });

  it('should change input value and component attribute, insertInfo, on change', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    component.ngOnInit();
    fixture.detectChanges();

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );

    const lengthElement = durationInfoContainers[0];
    expect(lengthElement).toBeTruthy();
    lengthElement.triggerEventHandler('change', {
      target: { value: 30, name: lengthElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(lengthElement.nativeElement.value).toBe('30');
    expect(component.insertInfo.length).toBe(30);

    const volumesElement = durationInfoContainers[1];
    expect(volumesElement).toBeTruthy();
    volumesElement.triggerEventHandler('change', {
      target: { value: 2, name: volumesElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(volumesElement.nativeElement.value).toBe('2');
    expect(component.insertInfo.volumes).toBe(2);

    const scoreElement = debugElement.query(By.css('.score-container select'));
    expect(scoreElement).toBeTruthy();
    scoreElement.triggerEventHandler('change', {
      target: { value: 9, name: scoreElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(scoreElement.nativeElement.value).toBe('9');
    expect(component.insertInfo.score).toBe(9);

    const statusElement = debugElement.query(
      By.css('.status-container select')
    );
    expect(statusElement).toBeTruthy();
    statusElement.triggerEventHandler('change', {
      target: {
        value: statusNameArray[1],
        name: statusElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(statusElement.nativeElement.value).toBe(statusNameArray[1]);
    expect(component.insertInfo.status).toBe(statusNameArray[1]);

    const notesElement = debugElement.query(
      By.css('.notes-container textarea')
    );
    expect(notesElement).toBeTruthy();
    notesElement.triggerEventHandler('change', {
      target: {
        value: 'Comentário muito criativo',
        name: notesElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(notesElement.nativeElement.value).toBe('Comentário muito criativo');
    expect(component.insertInfo.notes).toBe('Comentário muito criativo');
  });

  it('should not pass max length and volume value', () => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    component.ngOnInit();
    fixture.detectChanges();

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );

    const lengthElement = durationInfoContainers[0];
    expect(lengthElement).toBeTruthy();
    lengthElement.triggerEventHandler('change', {
      target: { value: 3819831928, name: lengthElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(lengthElement.nativeElement.value).toBe(
      `${mediaWithVolumes.length}`
    );
    expect(component.insertInfo.length).toBe(mediaWithVolumes.length);

    const volumesElement = durationInfoContainers[1];
    expect(volumesElement).toBeTruthy();
    volumesElement.triggerEventHandler('change', {
      target: {
        value: 219293183102930,
        name: volumesElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(volumesElement.nativeElement.value).toBe(
      `${mediaWithVolumes.volumes}`
    );
    expect(component.insertInfo.volumes).toBe(
      mediaWithVolumes.volumes as number
    );
  });

  it('should redirect to login if token isnt in localStorage', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    spyOn(localStorageService, 'getToken').and.returnValue(null);
    component.ngOnInit();
    fixture.detectChanges();

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );

    const lengthElement = durationInfoContainers[0];
    lengthElement.triggerEventHandler('change', {
      target: { value: 30, name: lengthElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.length).toBe(30);

    const volumesElement = durationInfoContainers[1];
    volumesElement.triggerEventHandler('change', {
      target: { value: 2, name: volumesElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.volumes).toBe(2);

    const scoreElement = debugElement.query(By.css('.score-container select'));
    scoreElement.triggerEventHandler('change', {
      target: { value: 9, name: scoreElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.score).toBe(9);

    const statusElement = debugElement.query(
      By.css('.status-container select')
    );
    statusElement.triggerEventHandler('change', {
      target: {
        value: statusNameArray[1],
        name: statusElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(component.insertInfo.status).toBe(statusNameArray[1]);

    const notesElement = debugElement.query(
      By.css('.notes-container textarea')
    );
    expect(notesElement).toBeTruthy();
    notesElement.triggerEventHandler('change', {
      target: {
        value: 'Comentário muito criativo',
        name: notesElement.attributes['name'],
      },
    });

    const sendBtn = debugElement.query(By.css('.send-insert-info-btn'));
    sendBtn.triggerEventHandler('click', {});
    tick();
    expect(router.url).toBe('/login');
  }));

  it('should redirect to login if token is invalid', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    spyOn(localStorageService, 'getToken').and.returnValue('invalid token');
    spyOn(listService, 'insertItem').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({ error: { message: 'Token inválido' } })
      )
    );
    component.ngOnInit();
    fixture.detectChanges();

    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );

    const lengthElement = durationInfoContainers[0];
    lengthElement.triggerEventHandler('change', {
      target: { value: 30, name: lengthElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.length).toBe(30);

    const volumesElement = durationInfoContainers[1];
    volumesElement.triggerEventHandler('change', {
      target: { value: 2, name: volumesElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.volumes).toBe(2);

    const scoreElement = debugElement.query(By.css('.score-container select'));
    scoreElement.triggerEventHandler('change', {
      target: { value: 9, name: scoreElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.score).toBe(9);

    const statusElement = debugElement.query(
      By.css('.status-container select')
    );
    statusElement.triggerEventHandler('change', {
      target: {
        value: statusNameArray[1],
        name: statusElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(component.insertInfo.status).toBe(statusNameArray[1]);

    const notesElement = debugElement.query(
      By.css('.notes-container textarea')
    );
    expect(notesElement).toBeTruthy();
    notesElement.triggerEventHandler('change', {
      target: {
        value: 'Comentário muito criativo',
        name: notesElement.attributes['name'],
      },
    });

    const sendBtn = debugElement.query(By.css('.send-insert-info-btn'));
    sendBtn.triggerEventHandler('click', {});
    tick();
    expect(router.url).toBe('/login');
  }));

  it('should redirect to home when insert succeeded', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(mediaService, 'getMediaById').and.returnValue(of(mediaWithVolumes));
    spyOn(localStorageService, 'getToken').and.returnValue('valid token');
    spyOn(listService, 'insertItem').and.returnValue(of({ message: "Deu bom" }));
    router.navigate(['media/insert/1']);
    component.ngOnInit();
    fixture.detectChanges();
    const durationInfoContainers = debugElement.queryAll(
      By.css('.duration-info-container input')
    );
    const lengthElement = durationInfoContainers[0];
    lengthElement.triggerEventHandler('change', {
      target: { value: 30, name: lengthElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.length).toBe(30);

    const volumesElement = durationInfoContainers[1];
    volumesElement.triggerEventHandler('change', {
      target: { value: 2, name: volumesElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.volumes).toBe(2);

    const scoreElement = debugElement.query(By.css('.score-container select'));
    scoreElement.triggerEventHandler('change', {
      target: { value: 9, name: scoreElement.attributes['name'] },
    });
    fixture.detectChanges();
    expect(component.insertInfo.score).toBe(9);

    const statusElement = debugElement.query(
      By.css('.status-container select')
    );
    statusElement.triggerEventHandler('change', {
      target: {
        value: statusNameArray[1],
        name: statusElement.attributes['name'],
      },
    });
    fixture.detectChanges();
    expect(component.insertInfo.status).toBe(statusNameArray[1]);

    const notesElement = debugElement.query(
      By.css('.notes-container textarea')
    );
    expect(notesElement).toBeTruthy();
    notesElement.triggerEventHandler('change', {
      target: {
        value: 'Comentário muito criativo',
        name: notesElement.attributes['name'],
      },
    });

    const sendBtn = debugElement.query(By.css('.send-insert-info-btn'));
    sendBtn.triggerEventHandler('click', {});
    tick();
    expect(router.url).toBe("/")
  }));
});
