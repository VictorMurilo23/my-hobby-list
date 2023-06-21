import { Component, OnInit } from '@angular/core';
import IMedia from 'src/app/interfaces/IMedia';
import IMediaBody from 'src/app/interfaces/IMediaBody';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private recentAdd: IMedia[] = [];
  constructor(private mediaService: MediaService) {
  }

  ngOnInit(): void {
    this.mediaService.getRecent().subscribe({
      next: (data: IMediaBody) => {
        this.recentAdd = [...data.medias];
      }
    })
  }

  getRecentAdd() {
    return this.recentAdd;
  }
}
