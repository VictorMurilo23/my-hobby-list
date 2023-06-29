import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import IMedia from 'src/app/interfaces/IMedia';
import { MediaService } from 'src/app/services/media.service';
import { statusNameArray } from 'src/app/utils/statusNameArray';

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent extends ErrorMessage implements OnInit {
  public mediaInfo!: IMedia | null | undefined;
  public insertStatusName: string[] = statusNameArray;

  constructor(private mediaService: MediaService, private activatedRoute: ActivatedRoute) {
    super("");
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(
      concatMap((params: ParamMap) => {
        const idParam = params.get("id");
        if (idParam !== null) {
          return this.mediaService.getMediaById(idParam);
        }
        return EMPTY;
      })
    )
    .subscribe({
      next: (data: IMedia) => {
        this.mediaInfo = data;
      },
      error: () => {
        this.mediaInfo = null;
      }
    });
  }
}
