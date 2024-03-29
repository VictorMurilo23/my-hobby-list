import { TestBed } from '@angular/core/testing';

import { CharacterService } from './character.service';
import { HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';

describe('CharacterService', () => {
  let service: CharacterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [CharacterService]
    });
    service = TestBed.inject(CharacterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a Observable type in getCharacters function', () => {
    expect(service.getCharacters("")).toBeInstanceOf(Observable);
  });
});
