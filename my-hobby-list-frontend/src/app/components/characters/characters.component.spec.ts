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

describe('CharactersComponent', () => {
  let component: CharactersComponent;
  let fixture: ComponentFixture<CharactersComponent>;
  let characterService: CharacterService;
  let router: Router;

  const characters: char[] = [
    {
      character: { characterInfo: 'teste1', id: 1, name: 'Personagem 1' },
      characterRole: 'Personagem principal',
    },
    {
      character: { characterInfo: 'teste2', id: 2, name: 'Personagem 2' },
      characterRole: 'Personagem secundário',
    },
    {
      character: { characterInfo: 'teste3', id: 3, name: 'Personagem 3' },
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
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
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
    component.mediaName = 'GTO';
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

  it('should redirect on click', fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(characterService, 'getCharacters').and.returnValue(
      of({ characters })
    );
    component.mediaName = 'GTO';
    component.ngOnInit();
    fixture.detectChanges();
    expect(characterService.getCharacters).toHaveBeenCalled();

    const charCard = debugElement.query(By.css(".char-card"));
    charCard.nativeElement.click();
    tick();
    fixture.detectChanges();
    expect(router.url).toBe("/character/1");
  }));
});
