import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  private searchContent = "";

  constructor(private router: Router) {}

  search() {
    this.router.navigate(["/search"], { queryParams: { name: this.searchContent } });
  }

  setSearchContent(event: Event) {
    const { value } = event.target as HTMLInputElement;
    this.searchContent = value;
  }
}
