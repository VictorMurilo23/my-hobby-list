import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { RegistryComponent } from './registry.component';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { of, throwError } from 'rxjs';
import { ErrorMessageComponent } from 'src/app/components/error-message/error-message.component';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { LocalStorageService } from 'src/app/services/local-storage.service';

describe('RegistryComponent', () => {
  let component: RegistryComponent;
  let fixture: ComponentFixture<RegistryComponent>;
  let router: Router;
  let userService: UserService;
  let localStorageService: LocalStorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [RegistryComponent, ErrorMessageComponent],
    }).compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(RegistryComponent);
    component = fixture.componentInstance;
    userService = fixture.debugElement.injector.get(UserService);
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

  it('should call saveUsername when username input changes', () => {
    spyOn(component, 'saveUsername');
    const { debugElement } = fixture;
    const usernameInput = debugElement.query(By.css('[name="username"]'));
    expect(usernameInput).toBeTruthy();
    usernameInput.triggerEventHandler('keyup', { target: { value: 'Teste' } });
    expect(component.saveUsername).toHaveBeenCalled();
  });

  it('should call savePassword when password input changes', () => {
    spyOn(component, 'savePassword');
    const { debugElement } = fixture;
    const passwordInput = debugElement.query(By.css('[name="password"]'));
    expect(passwordInput).toBeTruthy();
    passwordInput.triggerEventHandler('keyup', { target: { value: '1234' } });
    expect(component.savePassword).toHaveBeenCalled();
  });

  it('should be possible to do register with sucess', fakeAsync(() => {
    const { debugElement } = fixture;
    const token = "token válido";
    spyOn(userService, "register").and.returnValue(of({ token }));
    spyOn(localStorageService, "setToken");
    router.navigate(['/login']);
    tick();

    const passwordInput = debugElement.query(By.css('[name="password"]'));
    expect(passwordInput).toBeTruthy();
    passwordInput.triggerEventHandler('keyup', { target: { value: '1234' } });
    expect(component["password"]).toBe("1234");

    const usernameInput = debugElement.query(By.css('[name="username"]'));
    expect(usernameInput).toBeTruthy();
    usernameInput.triggerEventHandler('keyup', { target: { value: 'Teste' } });
    expect(component["username"]).toBe("Teste");

    const emailInput = debugElement.query(By.css('[name="email"]'));
    expect(emailInput).toBeTruthy();
    emailInput.triggerEventHandler('keyup', {
      target: { value: 'email@email.com' },
    });
    expect(component["email"]).toBe("email@email.com");

    const sendBtn = debugElement.query(By.css(".send-btn"));
    sendBtn.nativeElement.click();
    expect(userService.register).toHaveBeenCalled();
    expect(localStorageService.setToken).toHaveBeenCalledOnceWith(token);
    tick();
    expect(router.url).toBe("/");
  }));

  it('should render error message on observable error', () => {
    spyOn(userService, 'register').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            error: { message: 'Insira um email com o formato válido!' },
          })
      )
    );

    const compiled = fixture.debugElement.nativeElement;
    const emailInput = compiled.querySelector('[name="email"]');
    expect(emailInput).toBeTruthy();
    emailInput.value = 'email';

    const passwordInput = compiled.querySelector('[name="password"]');
    expect(passwordInput).toBeTruthy();
    passwordInput.value = 'teshiuywg';

    const usernameInput = compiled.querySelector('[name="username"]');
    expect(usernameInput).toBeTruthy();
    usernameInput.value = 'Victor';

    const sendBtn = compiled.querySelector('.send-btn');
    expect(sendBtn).toBeTruthy();
    sendBtn.click();

    expect(userService.register).toHaveBeenCalled();

    const errorMessage = compiled.querySelector(
      '.error-message-container .error-message'
    );
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe(
      'Insira um email com o formato válido!'
    );
  });

  it('should be possible to close error message', () => {
    spyOn(userService, 'register').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            error: { message: 'Insira um email com o formato válido!' },
          })
      )
    );

    const compiled = fixture.debugElement.nativeElement;
    const emailInput = compiled.querySelector('[name="email"]');
    expect(emailInput).toBeTruthy();
    emailInput.value = 'email';

    const passwordInput = compiled.querySelector('[name="password"]');
    expect(passwordInput).toBeTruthy();
    passwordInput.value = 'teshiuywg';

    const usernameInput = compiled.querySelector('[name="username"]');
    expect(usernameInput).toBeTruthy();
    usernameInput.value = 'Victor';

    const sendBtn = compiled.querySelector('.send-btn');
    expect(sendBtn).toBeTruthy();
    sendBtn.click();

    expect(userService.register).toHaveBeenCalled();

    const errorMessage = compiled.querySelector(
      '.error-message-container .error-message'
    );
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe(
      'Insira um email com o formato válido!'
    );

    const closeErrorMessageBtn = compiled.querySelector(
      '.error-message-container button'
    );
    expect(closeErrorMessageBtn).toBeTruthy();
    closeErrorMessageBtn.click();
    expect(
      compiled.querySelector('.error-message-container button')
    ).toBeFalsy();
  });
});
