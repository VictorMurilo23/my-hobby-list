import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-change-profile-image',
  templateUrl: './change-profile-image.component.html',
  styleUrls: ['./change-profile-image.component.css']
})
export class ChangeProfileImageComponent implements OnInit {
  private imagesUrls: string[] = [];
  private errorMessage!: string;
  readonly env = environment;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllProfileImagesOptions().subscribe({
      next: (data) => {
        this.imagesUrls = data.images;
      },
      error: () => {
        this.errorMessage = "Ops! Algo deu errado";
      }
    })
  }

  public getImages() {
    return this.imagesUrls;
  }

  public getErrorMessage() {
    return this.errorMessage;
  }
}
