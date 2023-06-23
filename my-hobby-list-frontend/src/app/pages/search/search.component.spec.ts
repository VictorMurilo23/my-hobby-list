import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';

import { SearchComponent } from './search.component';
import { SearchBarComponent } from 'src/app/components/search-bar/search-bar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MediaService } from 'src/app/services/media.service';
import { of } from 'rxjs';
import IMedia from 'src/app/interfaces/IMedia';
import { HeaderComponent } from 'src/app/components/header/header.component';
import { AppComponent } from 'src/app/app.component';
import { HomeComponent } from '../home/home.component';
import { MediaCardComponent } from 'src/app/components/media-card/media-card.component';
import routes from 'src/app/app.routes';

describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;
  let router: Router;

  const mediasList: IMedia[] = [
    {
      id: 1,
      image: 'url',
      length: 140,
      volumes: null,
      name: 'teste1',
      status: {
        id: 1,
        status: 'Teste1',
      },
      type: {
        id: 1,
        type: '1etseT',
      },
    },
    {
      id: 2,
      image: 'url2',
      length: 160,
      volumes: null,
      name: 'teste2',
      status: {
        id: 2,
        status: 'Teste2',
      },
      type: {
        id: 2,
        type: '2etseT',
      },
    },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        HttpClientModule,
      ],
      declarations: [
        SearchComponent,
        MediaCardComponent,
        HomeComponent,
        HeaderComponent,
        AppComponent,
        SearchBarComponent,
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render cards on search', fakeAsync(() => {
    const { debugElement } = fixture;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'searchByName').and.returnValue(of({ medias: mediasList }));
    router.navigate(['/search'], { queryParams: { name: 'teste' } });
    tick();
    expect(router.url).toBe('/search?name=teste');
    expect(service.searchByName).toHaveBeenCalled();
    fixture.detectChanges();

    const mediaCards =
      debugElement.nativeElement.querySelectorAll('.media-card');
    expect(mediaCards.length).toBe(2);
    expect(mediaCards[0].textContent).toBe('teste1');
  }));

  it('should not render cards when name param is empty', fakeAsync(() => {
    const { debugElement } = fixture;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'searchByName');
    router.navigate(['/search'], { queryParams: { name: '' } });
    tick();
    expect(router.url).toBe('/search?name=');
    expect(service.searchByName).not.toHaveBeenCalled();
    fixture.detectChanges();

    const mediaCards =
      debugElement.nativeElement.querySelectorAll('.media-card');
    expect(mediaCards.length).toBe(0);
  }));

  it('should be possible to use search bar in search page', fakeAsync(() => {
    const fixtureApp = TestBed.createComponent(AppComponent);
    const { debugElement } = fixtureApp;
    let service = debugElement.injector.get(MediaService);
    spyOn(service, 'searchByName').and.returnValue(of({ medias: mediasList }));
    router.navigate(['/search'], { queryParams: { name: '' } });
    tick();
    expect(router.url).toBe('/search?name=');
    expect(service.searchByName).not.toHaveBeenCalled();
    fixtureApp.detectChanges();
    const mediaCards =
      debugElement.nativeElement.querySelectorAll('.media-card');
    expect(mediaCards.length).toBe(0);

    fixtureApp.detectChanges();
    const searchInput = debugElement.query(By.css('.search-input'));
    expect(searchInput).toBeTruthy();
    searchInput.triggerEventHandler('change', { target: { value: 'Teste' } });

    const searchBtn = debugElement.query(
      By.css('.search-bar-container button')
    );
    expect(searchBtn).toBeTruthy();
    searchBtn.triggerEventHandler('click', null);
    tick();
    fixtureApp.detectChanges();

    expect(
      debugElement.nativeElement.querySelectorAll('.media-card').length
    ).toBe(2);
  }));
});
