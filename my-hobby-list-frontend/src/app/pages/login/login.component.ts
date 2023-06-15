import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private email: string = "";
  private password: string = "";
  private errorMessage: string = "";

  constructor(private service: LoginService) { }

  saveEmail(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.email = input.value;
  }

  savePassword(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.password = input.value;
  }

  login(): void {
    try {
      const token = this.service.reqLogin(this.email, this.password).subscribe({
        next: (data) => data.token,
        error: (error: HttpErrorResponse) => this.errorMessage = error.error.message
      });
    } catch(error: unknown) {
      if (error instanceof Error) this.setErrorMessage(error.message);
    }
  }

  getErrorMessage(): string {
    return this.errorMessage;
  }

  setErrorMessage(message: string = ""): void {
    this.errorMessage = message;
  }
}
