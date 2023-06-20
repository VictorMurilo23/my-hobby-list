import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { throwError } from 'rxjs';
import { ErrorMessageComponent } from 'src/app/components/error-message/error-message.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ LoginComponent, ErrorMessageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should render error message on observable error", () => {
    let service = fixture.debugElement.injector.get(UserService);
    spyOn(service, "login").and.returnValue(throwError(() => new HttpErrorResponse({ error: { message: "Senha inválida!" } })));

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

    expect(service.login).toHaveBeenCalled();

    const errorMessage = compiled.querySelector('.error-message-container .error-message');
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe("Senha inválida!");
  });

  it("should be possible to close error message", () => {
    let service = fixture.debugElement.injector.get(UserService);
    spyOn(service, "login").and.returnValue(throwError(() => new HttpErrorResponse({ error: { message: "Insira um email com o formato válido!" } })));

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

    expect(service.login).toHaveBeenCalled();

    const errorMessage = compiled.querySelector('.error-message-container .error-message');
    expect(errorMessage).toBeTruthy();
    expect(errorMessage.textContent).toBe("Insira um email com o formato válido!");

    const closeErrorMessageBtn = compiled.querySelector('.error-message-container button');
    expect(closeErrorMessageBtn).toBeTruthy();
    closeErrorMessageBtn.click();
    expect(compiled.querySelector('.error-message-container button')).toBeFalsy()
  });
});
