import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private email: string = "";
  private password: string = "";

  constructor()  {}

  saveEmail(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.email = input.value;
  }

  savePassword(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.password = input.value;
  }

  login(): void {
  }
}
