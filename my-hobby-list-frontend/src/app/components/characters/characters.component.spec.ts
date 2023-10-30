import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { CharactersComponent } from './characters.component';
import { HttpClientModule } from '@angular/common/http';
import { CharacterService } from 'src/app/services/character.service';
import { of } from 'rxjs';
import { char } from 'src/app/interfaces/ICharacters';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import routes from 'src/app/app.routes';
import { MediaService } from 'src/app/services/media.service';

describe('CharactersComponent', () => {
  let component: CharactersComponent;
  let fixture: ComponentFixture<CharactersComponent>;
  let characterService: CharacterService;
  let mediaService: MediaService;
  let router: Router;

  const characters: char[] = [
    {
      character: { characterImageUrl: 'teste1', id: 1, name: 'Personagem 1' },
      characterRole: 'Personagem principal',
    },
    {
      character: { characterImageUrl: 'teste2', id: 2, name: 'Personagem 2' },
      characterRole: 'Personagem secundário',
    },
    {
      character: { characterImageUrl: 'teste3', id: 3, name: 'Personagem 3' },
      characterRole: 'Personagem secundário',
    },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [CharactersComponent],
      providers: [CharacterService],
    }).compileComponents();

    fixture = TestBed.createComponent(CharactersComponent);
    characterService = fixture.debugElement.injector.get(CharacterService);
    mediaService = fixture.debugElement.injector.get(MediaService);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    spyOn(mediaService, "getMediaName").and.returnValue("GTO");
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load characters', () => {
    const { debugElement } = fixture;
    spyOn(characterService, 'getCharacters').and.returnValue(
      of({ characters })
    );
    component.ngOnInit();
    fixture.detectChanges();
    expect(characterService.getCharacters).toHaveBeenCalled();

    const chars = debugElement.queryAll(By.css(".char-card"));
    expect(chars.length).toBe(3);

    expect(chars[0].query(By.css(".char-name")).nativeElement.textContent).toBe("Personagem 1");
    expect(chars[0].query(By.css(".char-role")).nativeElement.textContent).toBe("Personagem principal");

    expect(chars[1].query(By.css(".char-name")).nativeElement.textContent).toBe("Personagem 2");
    expect(chars[1].query(By.css(".char-role")).nativeElement.textContent).toBe("Personagem secundário");

    expect(chars[2].query(By.css(".char-name")).nativeElement.textContent).toBe("Personagem 3");
    expect(chars[2].query(By.css(".char-role")).nativeElement.textContent).toBe("Personagem secundário");
  });
});
