import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public username! : string | null;
  constructor(private router: Router, private userService: UserService) {
    this.router.events.subscribe((ev) => {
      if (ev instanceof NavigationEnd) { 
        this.parseJwt();
      }
    });
  }

  private parseJwt(): void {
    this.username = this.userService.getUsernameFromToken();
  }

  ngOnInit(): void {
    this.parseJwt();
  }

  logout() {
    this.userService.logout();
  }
}
