import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IMediaCard from 'src/app/interfaces/IMediaCard';
import IMediaCardsBody from 'src/app/interfaces/IMediaCardsBody';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  public mediaName = '';
  private mediasList: IMediaCard[] = [];
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
        next: (data: IMediaCardsBody) => {
          this.mediasList = [...data.medias];
        },
      });
  }

  ngOnInit(): void {
    this.searchMedias();
  }

  getMediasList(): IMediaCard[] {
    return this.mediasList;
  }
}
