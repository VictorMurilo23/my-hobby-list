import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { of, throwError } from 'rxjs';
import { ErrorMessageComponent } from 'src/app/components/error-message/error-message.component';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let router: Router;
  let userService: UserService;
  let localStorageService: LocalStorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ LoginComponent, ErrorMessageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = fixture.debugElement.injector.get(UserService);
    router = TestBed.inject(Router);
    localStorageService = fixture.debugElement.injector.get(LocalStorageService);
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call saveEmail when email input changes', () => {
    spyOn(component, 'saveEmail');
    const { debugElement } = fixture;
    const emailInput = debugElement.query(By.css('[name="email"]'));
    expect(emailInput).toBeTruthy();
    emailInput.triggerEventHandler('keyup', {
      target: { value: 'email@email.com' },
    });
    expect(component.saveEmail).toHaveBeenCalled();
  });

  it('should call savePassword when password input changes', () => {
    spyOn(component, 'savePassword');
    const { debugElement } = fixture;
    const passwordInput = debugElement.query(By.css('[name="password"]'));
    expect(passwordInput).toBeTruthy();
    passwordInput.triggerEventHandler('keyup', { target: { value: '1234' } });
    expect(component.savePassword).toHaveBeenCalled();
  });

  it('should be possible to do login with success', fakeAsync(() => {
    const { debugElement } = fixture;
    const token = "token válido";
    spyOn(userService, "login").and.returnValue(of({ token }));
    spyOn(localStorageService, "setToken");
    router.navigate(['/register']);
    tick();

    const passwordInput = debugElement.query(By.css('[name="password"]'));
    expect(passwordInput).toBeTruthy();
    passwordInput.triggerEventHandler('keyup', { target: { value: '1234' } });
    expect(component["password"]).toBe("1234");

    const emailInput = debugElement.query(By.css('[name="email"]'));
    expect(emailInput).toBeTruthy();
    emailInput.triggerEventHandler('keyup', {
      target: { value: 'email@email.com' },
    });
    expect(component["email"]).toBe("email@email.com");

    const sendBtn = debugElement.query(By.css(".send-btn"));
    sendBtn.nativeElement.click();
    expect(userService.login).toHaveBeenCalled();
    expect(localStorageService.setToken).toHaveBeenCalledOnceWith(token);
    tick();
    expect(router.url).toBe("/");
  }));

  it("should render error message on observable error", () => {
    spyOn(userService, "login").and.returnValue(throwError(() => new HttpErrorResponse({ error: { message: "Senha inválida!" } })));

    const compiled = fixture.debugElement.nativeElement;
    const emailInput = compiled.querySelector('[name="email"]');
    expect(emailInput).toBeTruthy();
    emailInput.value = "email@gmail.com";

    const passwordInput = compiled.querySelector('[name="password"]');
    expect(passwordInput).toBeTruthy();
    passwordInput.value = "teshiuywg"

    const sendBtn = compiled.querySelector('.send-btn');
    expect(sendBtn).toBeTruthy();
    sendBtn.click();

    expect(userService.login).toHaveBeenCalled();

    const errorMessage = compiled.querySelector('.error-message-container .error-message');
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe("Senha inválida!");
  });

  it("should be possible to close error message", () => {
    spyOn(userService, "login").and.returnValue(throwError(() => new HttpErrorResponse({ error: { message: "Insira um email com o formato válido!" } })));

    const compiled = fixture.debugElement.nativeElement;
    const emailInput = compiled.querySelector('[name="email"]');
    expect(emailInput).toBeTruthy();
    emailInput.value = "email";

    const passwordInput = compiled.querySelector('[name="password"]');
    expect(passwordInput).toBeTruthy();
    passwordInput.value = "teshiuywg"

    const sendBtn = compiled.querySelector('.send-btn');
    expect(sendBtn).toBeTruthy();
    sendBtn.click();

    expect(userService.login).toHaveBeenCalled();

    const errorMessage = compiled.querySelector('.error-message-container .error-message');
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe("Insira um email com o formato válido!");

    const closeErrorMessageBtn = compiled.querySelector('.error-message-container button');
    expect(closeErrorMessageBtn).toBeTruthy();
    closeErrorMessageBtn.click();
    expect(compiled.querySelector('.error-message-container button')).toBeFalsy()
  });
});
