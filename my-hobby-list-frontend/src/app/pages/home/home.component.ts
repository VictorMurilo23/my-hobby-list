import { Component, OnInit } from '@angular/core';
import IMediaCard from 'src/app/interfaces/IMediaCard';
import IMediaCardsBody from 'src/app/interfaces/IMediaCardsBody';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private recentAdd: IMediaCard[] = [];
  constructor(private mediaService: MediaService) {
  }

  ngOnInit(): void {
    this.mediaService.getRecent().subscribe({
      next: (data: IMediaCardsBody) => {
        this.recentAdd = [...data.medias];
      }
    })
  }

  getRecentAdd() {
    return this.recentAdd;
  }
}
