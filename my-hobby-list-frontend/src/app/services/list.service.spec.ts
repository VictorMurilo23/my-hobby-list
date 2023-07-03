import { TestBed } from '@angular/core/testing';

import { ListService } from './list.service';
import { HttpClientModule } from '@angular/common/http';

describe('ListService', () => {
  let service: ListService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ListService]
    });
    service = TestBed.inject(ListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
