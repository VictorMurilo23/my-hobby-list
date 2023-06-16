import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import IMedia from 'src/app/interfaces/IMedia';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-media-card',
  templateUrl: './media-card.component.html',
  styleUrls: ['./media-card.component.css']
})
export class MediaCardComponent implements OnInit {
  @Input() mediaName: string = "";
  @Input() imageUrl: string = "";
  @Input() mediaId!: number;
  
  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.imageUrl = `${environment.apiUrl}/${this.imageUrl}`
  }

  redirectToMediaPage() {
    this.router.navigateByUrl(`/media/${this.mediaId}`)
  }
}
