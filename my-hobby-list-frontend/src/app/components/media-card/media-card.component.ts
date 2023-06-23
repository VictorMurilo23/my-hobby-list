import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-media-card',
  templateUrl: './media-card.component.html',
  styleUrls: ['./media-card.component.css']
})
export class MediaCardComponent implements OnInit {
  @Input() mediaName = "";
  @Input() imageUrl = "";
  @Input() mediaId!: number;
  
  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.imageUrl = `${environment.apiUrl}/${this.imageUrl}`;
  }

  redirectToMediaPage() {
    this.router.navigateByUrl(`/media/${this.mediaId}`);
  }
}
