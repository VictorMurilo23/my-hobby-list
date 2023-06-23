import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { SearchBarComponent } from './search-bar.component';
import { By } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import routes from 'src/app/app.routes';

describe('SearchBarComponent', () => {
  let component: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        RouterTestingModule.withRoutes(routes),
      ],
      declarations: [ SearchBarComponent ]
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(SearchBarComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should call setSearchContent in input change", () => {
    spyOn(component, "setSearchContent");
    const searchInput = fixture.debugElement.query(By.css('.search-input'));
    expect(searchInput).toBeTruthy();
    searchInput.triggerEventHandler("change", {target:{value: 'A'}});
    expect(component.setSearchContent).toHaveBeenCalled();
  });

  it("should redirect when click on search button", fakeAsync(() => {
    const { debugElement } = fixture;
    const searchInput = debugElement.query(By.css('.search-input'));
    expect(searchInput).toBeTruthy();
    searchInput.triggerEventHandler("change", {target:{value: 'Teste'}});

    const searchBtn = debugElement.query(By.css('.search-bar-container button'));
    expect(searchBtn).toBeTruthy();
    searchBtn.triggerEventHandler('click', null);
    tick();
    expect(router.url).toBe("/search?name=Teste");
  }));
});
