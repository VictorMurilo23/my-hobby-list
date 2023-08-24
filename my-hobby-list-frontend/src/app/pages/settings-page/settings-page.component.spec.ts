import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { SettingsPageComponent } from './settings-page.component';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { By } from '@angular/platform-browser';
import { ChangeProfileImageComponent } from 'src/app/components/change-profile-image/change-profile-image.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { LoginComponent } from '../login/login.component';

describe('SettingsPageComponent', () => {
  let component: SettingsPageComponent;
  let fixture: ComponentFixture<SettingsPageComponent>;
  let userService: UserService;
  let router: Router;
  let localStorage: LocalStorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ SettingsPageComponent, ChangeProfileImageComponent, LoginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SettingsPageComponent);
    userService = fixture.debugElement.injector.get(UserService);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    router = TestBed.inject(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to login when localStorage token is null', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(localStorage, 'getToken').and.returnValue(null);
    fixture.detectChanges();

    expect(router.url).toBe("/login")
  }));

  it('should redirect to change-profile-image on click', fakeAsync(() => {
    const { debugElement } = fixture;
    const changeProfileImageLink = debugElement.query(By.css(".change-profile-image-link"));
    changeProfileImageLink.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(router.url).toBe("/change-profile-image");
  }));
});
