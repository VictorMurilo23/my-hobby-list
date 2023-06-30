import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import IMedia from 'src/app/interfaces/IMedia';
import { ListService } from 'src/app/services/list.service';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { MediaService } from 'src/app/services/media.service';
import IInsertInfo from 'src/app/types/IInsertInfo';
import { statusNameArray } from 'src/app/utils/statusNameArray';

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
    length: 0,
    status: this.insertStatusName[0],
    score: 0,
    volumes: 0,
    notes: '',
  };

  constructor(
    private mediaService: MediaService,
    private activatedRoute: ActivatedRoute,
    private localStorage: LocalStorageService,
    private router: Router,
    private listService: ListService
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

  changeValue(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    switch (input.name) {
      case 'status-input':
        this.insertInfo.status = value;
        break;
      case 'length-input':
        if (
          typeof this.mediaInfo?.length === 'number' &&
          parseInt(value) > this.mediaInfo.length
        ) {
          this.insertInfo.length = this.mediaInfo.length;
        } else {
          this.insertInfo.length = parseInt(value);
        }
        break;
      case 'volumes-input':
        if (
          typeof this.mediaInfo?.volumes === 'number' &&
          parseInt(value) > this.mediaInfo.volumes
        ) {
          this.insertInfo.volumes = this.mediaInfo.volumes;
        } else {
          this.insertInfo.volumes = parseInt(value);
        }
        break;
      case 'score-input':
        this.insertInfo.score = parseInt(value);
        break;
      case 'notes-input':
        this.insertInfo.notes = value;
        break;
      default:
        return;
    }
  }

  async sendInfo() {
    const token = this.localStorage.getToken();
    if (token === null) {
      this.router.navigate(["/login"]);
      return;
    }
    super.setErrorMessage("")
    this.listService.insertItem(this.insertInfo, token).subscribe({
      next: (_data) => {
        this.router.navigate(["/"]);
      },
      error: (error: HttpErrorResponse) => super.setErrorMessage(error.error.message)
    });
  }
}
