import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-settings-page',
  templateUrl: './settings-page.component.html',
  styleUrls: ['./settings-page.component.css']
})
export class SettingsPageComponent implements OnInit {
  constructor(private localStorage: LocalStorageService, private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    if (this.localStorage.getToken() === null) {
      this.userService.logout();
    }
  }
}
