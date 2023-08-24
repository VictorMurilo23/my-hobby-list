import { HttpErrorResponse } from '@angular/common/http';
import {
  Component,
  ElementRef,
  OnInit,
} from '@angular/core';
import { Router } from '@angular/router';
import IProfile from 'src/app/interfaces/IProfile';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-change-profile-image',
  templateUrl: './change-profile-image.component.html',
  styleUrls: ['./change-profile-image.component.css'],
})
export class ChangeProfileImageComponent implements OnInit {
  private imagesUrls: string[] = [];
  private errorMessage!: string;
  private selectedImage = '';
  readonly env = environment;

  constructor(
    private userService: UserService,
    private localStorage: LocalStorageService,
    private router: Router,
    private el: ElementRef
  ) {}

  ngOnInit(): void {
    this.userService.getAllProfileImagesOptions().subscribe({
      next: (data) => {
        this.imagesUrls = data.images;
      },
      error: () => {
        this.errorMessage = 'Ops! Algo deu errado';
      },
    });
  }

  public getImages() {
    return this.imagesUrls;
  }

  public getErrorMessage() {
    return this.errorMessage;
  }

  public setSelectedImage(event: any) {
    const prevSelectedImage = this.el.nativeElement.querySelector('.selected');
    if (prevSelectedImage !== null) {
      prevSelectedImage.classList.remove('selected');
    }
    event.srcElement.classList.add('selected');
    this.selectedImage = event.target.getAttribute('src');
  }

  public changeProfileImage() {
    if (this.selectedImage === '') {
      return;
    }
    const token = this.localStorage.getToken();
    if (token === null) {
      this.router.navigate(['/login']);
      return;
    }
    this.userService.changeProfileImage(this.selectedImage, token).subscribe({
      next: (data: IProfile) => {
        this.router.navigate([`/profile/${data.username}`]);
      },
      error: (err: HttpErrorResponse) => {
        const errorMessage = err.error.message;
        if (errorMessage === 'User não encontrada!') {
          this.userService.logout();
          return;
        }
      },
    });
  }
}
