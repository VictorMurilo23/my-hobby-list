import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import IRegister from 'src/app/interfaces/IRegister';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent extends ErrorMessage {
  private email: string = "";
  private password: string = "";
  private username: string = ""; 

  constructor(private userService: UserService, private localStorage: LocalStorageService) {
    super("");
  }

  register() {
    const body: IRegister = { email: this.email, password: this.password, username: this.username};
    this.userService.register(body).subscribe({
      next: (data) => {
        this.localStorage.setToken(data.token);
      },
      error: (error: HttpErrorResponse) => super.setErrorMessage(error.error.message) 
    });
  }

  saveEmail(event: Event) {
    const input = event.target as HTMLInputElement;
    this.email = input.value;
  }

  savePassword(event: Event) {
    const input = event.target as HTMLInputElement;
    this.password = input.value;
  }

  saveUsername(event: Event) {
    const input = event.target as HTMLInputElement;
    this.username = input.value;
  }
}
