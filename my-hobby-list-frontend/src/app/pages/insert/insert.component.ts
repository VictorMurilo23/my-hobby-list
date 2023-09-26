import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import IMedia from 'src/app/interfaces/IMedia';
import { ListService } from 'src/app/services/list.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { MediaService } from 'src/app/services/media.service';
import { UserService } from 'src/app/services/user.service';
import IInsertInfo from 'src/app/types/IInsertInfo';
import { statusNameArray } from 'src/assets/statusNameArray';

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css'],
})
export class InsertComponent extends ErrorMessage implements OnInit {
  public mediaInfo!: IMedia | null | undefined;
  public insertStatusName: string[] = statusNameArray;
  public insertInfo: IInsertInfo = {
    mediaId: 0,
    progress: 0,
    status: this.insertStatusName[0],
    score: 1,
    volumes: 0,
    notes: '',
  };

  constructor(
    private mediaService: MediaService,
    private activatedRoute: ActivatedRoute,
    private localStorage: LocalStorageService,
    private router: Router,
    private listService: ListService,
    private userService: UserService,
  ) {
    super('');
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const idParam = params.get('id');
          if (idParam !== null) {
            return this.mediaService.getMediaById(idParam);
          }
          return EMPTY;
        })
      )
      .subscribe({
        next: (data: IMedia) => {
          this.insertInfo.mediaId = data.id;
          this.mediaInfo = data;
        },
        error: () => {
          this.mediaInfo = null;
        },
      });
  }

  public setNotes(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    this.insertInfo.notes = value;
  }

  public setScore(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    this.insertInfo.score = parseInt(value);
  }

  public setStatus(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    this.insertInfo.status = value;
  }

  public setVolumes(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    if (
      typeof this.mediaInfo?.volumes === 'number' &&
      parseInt(value) > this.mediaInfo.volumes
    ) {
      this.insertInfo.volumes = this.mediaInfo.volumes;
    } else {
      this.insertInfo.volumes = parseInt(value);
    }
  }

  public setProgress(event: Event) {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    if (
      typeof this.mediaInfo?.length === 'number' &&
      parseInt(value) > this.mediaInfo.length
    ) {
      this.insertInfo.progress = this.mediaInfo.length;
    } else {
      this.insertInfo.progress = parseInt(value);
    }
  }

  async sendInfo() {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.router.navigate(["/login"]);
      return;
    }
    super.setErrorMessage("");
    this.listService.insertItem(this.insertInfo, token).subscribe({
      next: (_data) => {
        this.router.navigate(["/"]);
      },
      error: (error: HttpErrorResponse) => {
        const message: string = error.error.message;
        if (message === "Token inválido") {
          this.userService.logout();
          return;
        }
        super.setErrorMessage(message);
      }
    });
  }
}
