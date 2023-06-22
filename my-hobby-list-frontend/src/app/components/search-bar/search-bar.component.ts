import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  public searchContent = "";

  constructor(private router: Router) {}

  async search() {
    await this.router.navigate(["/search"], { queryParams: { name: this.searchContent } });
    this.searchContent = "";
  }

  setSearchContent(event: Event): void {
    const { value } = event.target as HTMLInputElement;
    this.searchContent = value;
  }

  getSearchContent(): string {
    return this.searchContent;
  }
}
