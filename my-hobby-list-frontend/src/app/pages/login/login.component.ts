import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends ErrorMessage {
  private email: string = "";
  private password: string = "";

  constructor(private service: UserService) {
    super("");
  }

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
      const token = this.service.login(this.email, this.password).subscribe({
        next: (data) => data.token,
        error: (error: HttpErrorResponse) => super.setErrorMessage(error.error.message) 
      });
    } catch(error: unknown) {
      if (error instanceof Error) this.setErrorMessage(error.message);
    }
  }
}
