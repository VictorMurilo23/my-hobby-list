import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ChangeProfileImageComponent } from './change-profile-image.component';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import ProfileImages from 'src/app/types/ProfileImages';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';
import { LoginComponent } from 'src/app/pages/login/login.component';
import { SettingsPageComponent } from 'src/app/pages/settings-page/settings-page.component';
import IProfile from 'src/app/interfaces/IProfile';

describe('ChangeProfileImageComponent', () => {
  let component: ChangeProfileImageComponent;
  let fixture: ComponentFixture<ChangeProfileImageComponent>;
  let userService: UserService;
  let router: Router;
  let localStorage: LocalStorageService;

  const profileImages = { images: ["images/01.png", "images/02.png", "images/03.png"] };

  const profileInfo: IProfile = {
    joinedAt: "2023-07-07",
    profileImage: "/image",
    userDescription: "",
    username: "Victo"
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ChangeProfileImageComponent, LoginComponent, SettingsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeProfileImageComponent);
    userService = fixture.debugElement.injector.get(UserService);
    localStorage = fixture.debugElement.injector.get(LocalStorageService);
    router = TestBed.inject(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
    spyOn(localStorage, "getToken").and.returnValue("d");
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should render all profile images options', () => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    component.ngOnInit();
    fixture.detectChanges();

    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);
  });

  it('should add selected class on click', () => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    component.ngOnInit();
    fixture.detectChanges();

    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);

    images[0].nativeElement.click();
    fixture.detectChanges();
    expect(images[0].attributes["class"]?.includes("selected")).toBeTruthy();
  });

  it('should only have one element with selected class', () => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    component.ngOnInit();
    fixture.detectChanges();

    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);

    images[0].nativeElement.click();
    fixture.detectChanges();
    expect(images[0].attributes["class"]?.includes("selected")).toBeTruthy();

    images[1].nativeElement.click();
    fixture.detectChanges();
    expect(images[0].attributes["class"]?.includes("selected")).not.toBeTruthy();
    expect(images[1].attributes["class"]?.includes("selected")).toBeTruthy();
  });

  it('should change selectedImage on click', () => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    component.ngOnInit();
    fixture.detectChanges();

    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);

    images[0].nativeElement.click();
    fixture.detectChanges();
    expect(component["selectedImage"] as any).toEqual("http://localhost:3001/images/01.png");

    images[1].nativeElement.click();
    fixture.detectChanges();
    expect(component["selectedImage"] as any).toEqual("http://localhost:3001/images/02.png");
  });
  
  it('shouldn\'t call userService.changeProfileImage if a image isnt selected', () => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    spyOn(userService, "changeProfileImage").and.returnValue(of(profileInfo));
    component.ngOnInit();
    fixture.detectChanges();

    const btn = debugElement.query(By.css(".change-profile-image-btn"));
    expect(btn).toBeTruthy();
    btn.nativeElement.click();

    expect(userService.changeProfileImage).not.toHaveBeenCalled();
  });

  it('shouldn\'t call userService.changeProfileImage and redirect to /login if token is null', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    spyOn(userService, "changeProfileImage").and.returnValue(of(profileInfo));
    component.ngOnInit();
    localStorage.getToken = jasmine.createSpy().and.returnValue(null);
    fixture.detectChanges();
    
    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);
    
    images[0].nativeElement.click();
    
    const btn = debugElement.query(By.css(".change-profile-image-btn"));
    expect(btn).toBeTruthy();
    btn.nativeElement.click();
    tick();
    expect(router.url).toBe("/login")
    expect(userService.changeProfileImage).not.toHaveBeenCalled();
  }));

  it('should change profile image with success', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    spyOn(userService, "changeProfileImage").and.returnValue(of(profileInfo));
    component.ngOnInit();
    fixture.detectChanges();
    
    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);
    
    images[0].nativeElement.click();
    
    const btn = debugElement.query(By.css(".change-profile-image-btn"));
    expect(btn).toBeTruthy();
    btn.nativeElement.click();
    tick();
    expect(router.url).toBe("/profile/Victo")
    expect(userService.changeProfileImage).toHaveBeenCalledWith("http://localhost:3001/images/01.png", "d");
  }));

  it('should redirect to /login if userService.changeProfileImage return HttpError with "User não encontrada!"', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(userService, "getAllProfileImagesOptions").and.returnValue(of(profileImages));
    spyOn(userService, "changeProfileImage").and.returnValue(throwError(
      () =>
        new HttpErrorResponse({ error: { message: 'User não encontrada!' } })
    ));
    spyOn(localStorage, "removeToken");
    component.ngOnInit();
    fixture.detectChanges();
    
    const images = debugElement.queryAll(By.css(".profile-image-option"));
    expect(images.length).toBe(3);
    
    images[0].nativeElement.click();
    
    const btn = debugElement.query(By.css(".change-profile-image-btn"));
    expect(btn).toBeTruthy();
    btn.nativeElement.click();
    tick();
    expect(router.url).toBe("/login");
    expect(localStorage.removeToken).toHaveBeenCalled();
    expect(userService.changeProfileImage).toHaveBeenCalledWith("http://localhost:3001/images/01.png", "d");
  }));
});
