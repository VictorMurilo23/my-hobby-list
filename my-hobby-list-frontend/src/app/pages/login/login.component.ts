import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends ErrorMessage {
  private email: string = "";
  private password: string = "";

  constructor(private service: UserService, private localStorage: LocalStorageService) {
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
    this.service.login(this.email, this.password).subscribe({
      next: (data) => {
        this.localStorage.setToken(data.token)
      },
      error: (error: HttpErrorResponse) => super.setErrorMessage() 
    });
  }
}
