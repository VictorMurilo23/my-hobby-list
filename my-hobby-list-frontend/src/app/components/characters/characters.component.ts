import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import ICharacters, { char } from 'src/app/interfaces/ICharacters';
import { CharacterService } from 'src/app/services/character.service';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-characters',
  templateUrl: './characters.component.html',
  styleUrls: ['./characters.component.css']
})
export class CharactersComponent implements OnInit {
  public characters: char[] = [];

  constructor(private mediaService: MediaService, private characterService: CharacterService, private router: Router) {}

  ngOnInit(): void {
    const mediaName = this.mediaService.getMediaName();
    this.characterService.getCharacters(mediaName).subscribe({
      next: (data: ICharacters) => {
        this.characters = data.characters;
      }
    })
  }

  redirect(charId: number): void {
    this.router.navigate([`/character/${charId}`]);
  }
}
