import { Component } from '@angular/core';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { Buffer } from 'buffer';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  public username! : string | null;
  constructor(private localStorageService: LocalStorageService, private router: Router) {
    this.router.events.subscribe((ev) => {
      if (ev instanceof NavigationEnd) { 
        this.parseJwt();
      }
    });
  }

  private parseJwt(): void {
    try {
      const token = this.localStorageService.getToken();
      if (token === null) throw new Error();
      const payload = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      const { username } = payload;
      if (username === null) throw new Error();
      this.username = username;
    } catch (e) {
      this.localStorageService.removeToken();
      this.username = null;
    }
  }
}
