import { ComponentFixture, TestBed, async, fakeAsync } from '@angular/core/testing';

import { InsertComponent } from './insert.component';
import routes from 'src/app/app.routes';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { MediaService } from 'src/app/services/media.service';
import IMedia from 'src/app/interfaces/IMedia';
import { By } from '@angular/platform-browser';

describe('InsertComponent', () => {
  let component: InsertComponent;
  let fixture: ComponentFixture<InsertComponent>;
  let router: Router;
  const mediaWithoutVolumes: IMedia = {
    id: 1,
    image: 'gto-image',
    length: 208,
    volumes: null,
    name: 'GTO',
    status: {
      id: 1,
      status: 'Completo',
    },
    type: {
      id: 1,
      type: 'Manga',
    },
  };

  const mediaWithVolumes: IMedia = {
    ...mediaWithoutVolumes,
    volumes: 28
  };


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ InsertComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ id: 1 })),
          },
        },
      ],
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(InsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getMediaById on init', () => {
    let mediaService = fixture.debugElement.injector.get(MediaService);
    spyOn(mediaService, "getMediaById").and.returnValue(of(mediaWithoutVolumes));
    expect(mediaService.getMediaById).not.toHaveBeenCalled();
    component.ngOnInit();
    fixture.detectChanges();
    expect(mediaService.getMediaById).toHaveBeenCalled();
  });

  it("should render media info with volumes", () => {
    const { debugElement } = fixture;
    let mediaService = fixture.debugElement.injector.get(MediaService);
    spyOn(mediaService, "getMediaById").and.returnValue(of(mediaWithVolumes));
    component.ngOnInit();
    fixture.detectChanges();
    const mediaName = debugElement.nativeElement.querySelector(".media-name");
    expect(mediaName).toBeTruthy();
    expect(mediaName.textContent).toBe("GTO");
    
    const durationInfoContainers = debugElement.queryAll(By.css(".duration-info-container .length"));
    expect(durationInfoContainers.length).toBe(2);
    expect(durationInfoContainers[0].nativeElement.textContent).toBe(" / 208");
    expect(durationInfoContainers[1].nativeElement.textContent).toBe(" / 28");
  });

  it("should render media info without volumes", () => {
    const { debugElement } = fixture;
    let mediaService = fixture.debugElement.injector.get(MediaService);
    spyOn(mediaService, "getMediaById").and.returnValue(of(mediaWithoutVolumes));
    component.ngOnInit();
    fixture.detectChanges();
    const mediaName = debugElement.nativeElement.querySelector(".media-name");
    expect(mediaName).toBeTruthy();
    expect(mediaName.textContent).toBe("GTO");
    
    const durationInfoContainers = debugElement.queryAll(By.css(".duration-info-container .length"));
    expect(durationInfoContainers.length).toBe(1);
    expect(durationInfoContainers[0].nativeElement.textContent).toBe(" / 208");
  });
});
