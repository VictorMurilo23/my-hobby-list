import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-pagination[totalPages][getContent][currentPage]',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() totalPages!: number;
  @Input()
  getContent!: (pageNumber: number) => void;
  @Input() currentPage!: number

  public getTotalPages() {
    const arr = [];
    for (let index = 1; index <= this.totalPages; index += 1) {
      arr.push(index);
    }
    return arr;
  }

  public changePage(pageNum: number) {
    this.currentPage = pageNum;
    this.getContent(pageNum);
  }
}
