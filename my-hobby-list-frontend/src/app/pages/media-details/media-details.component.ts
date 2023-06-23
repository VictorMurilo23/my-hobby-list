import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IMedia from 'src/app/interfaces/IMedia';
import { MediaService } from 'src/app/services/media.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-media-details',
  templateUrl: './media-details.component.html',
  styleUrls: ['./media-details.component.css']
})
export class MediaDetailsComponent implements OnInit {
  private mediaInfo!: IMedia | null;
  public imageUrl!: string;
  public env = environment;

  constructor(private mediaService: MediaService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params: ParamMap) => {
          const idParam = params.get("id");
          if (idParam !== null) {
            console.log(this.mediaInfo)
            return this.mediaService.getMediaById(idParam);
          }
          return EMPTY;
        })
      )
      .subscribe({
        next: (data: IMedia) => {
          this.mediaInfo = data;
          console.log(data);
        },
        error: () => {
          this.mediaInfo = null;
        }
      });
  }

  getMediaInfo(): IMedia | null | undefined {
    return this.mediaInfo;
  }
}
