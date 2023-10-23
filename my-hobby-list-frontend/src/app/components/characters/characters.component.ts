import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import ICharacters, { char } from 'src/app/interfaces/ICharacters';
import { CharacterService } from 'src/app/services/character.service';
import { MediaService } from 'src/app/services/media.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-characters',
  templateUrl: './characters.component.html',
  styleUrls: ['./characters.component.css']
})
export class CharactersComponent implements OnInit {
  public characters: char[] = [];
  readonly env = environment;

  constructor(private mediaService: MediaService, private characterService: CharacterService) {}

  ngOnInit(): void {
    const mediaName = this.mediaService.getMediaName();
    this.characterService.getCharacters(mediaName).subscribe({
      next: (data: ICharacters) => {
        this.characters = data.characters;
      }
    })
  }
}
