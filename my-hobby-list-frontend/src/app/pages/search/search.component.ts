import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IMedia from 'src/app/interfaces/IMedia';
import IMediaBody from 'src/app/interfaces/IMediaBody';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  public mediaName = '';
  private mediasList: IMedia[] = [];
  constructor(
    private activatedRoute: ActivatedRoute,
    private mediaService: MediaService
  ) {}

  private searchMedias() {
    this.activatedRoute.queryParams
      .pipe(
        concatMap((params: any) => {
          const nameParam = params.name || '';
          this.mediaName = nameParam;
          if (nameParam !== '') {
            return this.mediaService.searchByName(this.mediaName);
          }
          this.mediasList = [];
          return EMPTY;
        })
      )
      .subscribe({
        next: (data: IMediaBody) => {
          this.mediasList = [...data.medias];
        },
      });
  }

  ngOnInit(): void {
    this.searchMedias();
  }

  getMediasList(): IMedia[] {
    return this.mediasList;
  }
}
