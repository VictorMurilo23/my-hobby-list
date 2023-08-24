import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let router: Router;
  let localStorageService: LocalStorageService;
  let userService: UserService;
  
  const validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJUZXN0ZSJ9.7v5OsWsTA4Y0df9CeUR04X5712w1pg3Vk-muu86RZdg"

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ HeaderComponent, SearchBarComponent ]
    })
    .compileComponents();
    
    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    localStorageService = fixture.debugElement.injector.get(LocalStorageService);
    userService = fixture.debugElement.injector.get(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should render login and register buttons when logged out", fakeAsync(() => {
    const { debugElement } = fixture;

    const loginBtn = debugElement.query(By.css(".login-redirect-button"));
    expect(loginBtn).toBeTruthy();
    loginBtn.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(router.url).toBe("/login");

    const registerBtn = debugElement.query(By.css(".register-redirect-button"));
    expect(registerBtn).toBeTruthy();
    registerBtn.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(router.url).toBe("/register");
  }));

  it("should render username when token is valid", () => {
    const { debugElement } = fixture;
    spyOn(localStorageService, "getToken").and.returnValue(validToken);
    component.ngOnInit();
    fixture.detectChanges();
    const userInfoContainer = debugElement.query(By.css(".user-info-container"));
    expect(userInfoContainer).toBeTruthy();

    const username = userInfoContainer.query(By.css(".username"));
    expect(username).toBeTruthy(); 
    expect(username.nativeElement.textContent).toBe("Teste")
  });

  it("should render a button that redirects to userlist when token is valid", fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(localStorageService, "getToken").and.returnValue(validToken);
    component.ngOnInit();
    fixture.detectChanges();
    const userInfoContainer = debugElement.query(By.css(".user-info-container"));
    expect(userInfoContainer).toBeTruthy();

    const linkToUserList = userInfoContainer.query(By.css(".link-to-userlist"));
    expect(linkToUserList).toBeTruthy();
    linkToUserList.nativeElement.click();
    tick();
    expect(router.url).toBe("/list/Teste");
  }));

  it("should redirect to home when home button is clicked", fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(localStorageService, "getToken").and.returnValue(validToken);
    component.ngOnInit();
    fixture.detectChanges();
    const userInfoContainer = debugElement.query(By.css(".user-info-container"));
    expect(userInfoContainer).toBeTruthy();

    const linkToUserList = userInfoContainer.query(By.css(".link-to-userlist"));
    expect(linkToUserList).toBeTruthy();
    linkToUserList.nativeElement.click();
    tick();
    expect(router.url).toBe("/list/Teste");

    const linkToHome = debugElement.query(By.css(".home-redirect-button"));
    expect(linkToHome).toBeTruthy();
    linkToHome.nativeElement.click();
    tick();
    expect(router.url).toBe("/");
  }));

  it("should update userinfo every route change", fakeAsync(() => {
    const { debugElement } = fixture;
    const loginBtn = debugElement.query(By.css(".login-redirect-button"));
    expect(loginBtn).toBeTruthy();
    loginBtn.nativeElement.click();
    tick();
    fixture.detectChanges();
    spyOn(localStorageService, "getToken").and.returnValue(validToken);

    expect(debugElement.query(By.css(".login-redirect-button"))).toBeTruthy();
    const homeBtn = debugElement.query(By.css(".home-redirect-button"));
    homeBtn.nativeElement.click();
    tick();
    fixture.detectChanges();

    expect(debugElement.query(By.css(".login-redirect-button"))).toBeNull();
    expect(debugElement.query(By.css(".register-redirect-button"))).toBeNull();
    const username = debugElement.query(By.css(".user-info-container")).query(By.css(".username"));
    expect(username).toBeTruthy(); 
    expect(username.nativeElement.textContent).toBe("Teste")
  }));

  it('should redirect to /login when logout button is clicked', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(localStorageService, "getToken").and.returnValue(validToken);
    component.ngOnInit();
    fixture.detectChanges();
    const logoutBtn = debugElement.query(By.css(".logout-btn"));
    expect(logoutBtn).toBeTruthy();
    logoutBtn.nativeElement.click();
    localStorageService.getToken = jasmine.createSpy().and.returnValue(null);
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const username = debugElement.query(By.css(".username"));
    expect(username).not.toBeTruthy();
    expect(router.url).toBe("/login");
  }));
});
