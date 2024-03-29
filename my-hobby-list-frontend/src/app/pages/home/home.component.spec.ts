import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { HttpClientModule } from '@angular/common/http';
import { MediaService } from 'src/app/services/media.service';
import { Observable } from 'rxjs';
import IMediaBody from 'src/app/interfaces/IMediaBody';
import { of } from 'rxjs';
import { MediaCardComponent } from 'src/app/components/media-card/media-card.component';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import routes from 'src/app/app.routes';

class mockService {
  public getRecent(): Observable<IMediaBody> {
    return of({
      medias: [
        {
          id: 1,
          name: 'Teste1',
          image: '/dwad',
          length: 20,
          status: 'Completo',
          type: 'Manga',
          volumes: 10,
        },
        {
          id: 2,
          name: 'Teste2',
          image: '/ffff',
          length: 20,
          status: 'Completo',
          type:'Manga',
          volumes: 10,
        },
      ],
    });
  }
}

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        RouterTestingModule.withRoutes(routes),
      ],
      declarations: [HomeComponent, MediaCardComponent],
      providers: [{ provide: MediaService, useClass: mockService }],
    }).compileComponents();

    router = TestBed.inject(Router);

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render media cards', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelectorAll('.media-card')).toHaveSize(2);
  });

  it('should render in the correct order', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(
      compiled.querySelectorAll('.media-card').item(0).textContent
    ).toEqual('Teste1');
    expect(
      compiled.querySelectorAll('.media-card').item(1).textContent
    ).toEqual('Teste2');
  });

  it('should redirect to correct order', fakeAsync(() => {
    const { debugElement } = fixture;

    const mediaCard = debugElement.query(By.css('.media-card'));
    mediaCard.triggerEventHandler('click', null);
    tick();
    expect(router.url).toBe("/media/1");
  }));
});
