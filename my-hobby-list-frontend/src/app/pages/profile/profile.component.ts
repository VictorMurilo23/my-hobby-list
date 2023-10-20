import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IProfile from 'src/app/interfaces/IProfile';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  private profileInfo!: IProfile | null;
  readonly env = environment;

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const username = params.get('username');
          if (username !== null) {
            return this.userService.getProfileInfo(username);
          }
          return EMPTY;
        })
      )
      .subscribe({
        next: (data: IProfile) => {
          this.profileInfo = data;
        },
        error: () => {
          this.profileInfo = null;
        },
      });
  }

  public getProfileInfo(): IProfile | null {
    return this.profileInfo;
  }

  public getJoinedDate(): string {
    const dateString = this.getProfileInfo()?.joinedAt;
    if (dateString !== undefined) {
      const dateObj = new Date(dateString +  " ");
      return `${dateObj.toLocaleString('pt-BR', {
        month: 'long',
      })} ${dateObj.getDate()}, ${dateObj.getFullYear()}`;
    }
    return '';
  }
}
