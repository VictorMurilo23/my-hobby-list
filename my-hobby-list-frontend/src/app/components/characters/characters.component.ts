import { Component, Input, OnInit } from '@angular/core';
import ICharacters, { char } from 'src/app/interfaces/ICharacters';
import { CharacterService } from 'src/app/services/character.service';

@Component({
  selector: 'app-characters',
  templateUrl: './characters.component.html',
  styleUrls: ['./characters.component.css']
})
export class CharactersComponent implements OnInit {
  public characters: char[] = []; 
  @Input() mediaName = "";

  constructor(private characterService: CharacterService) {}

  ngOnInit(): void {
    this.characterService.getCharacters(this.mediaName).subscribe({
      next: (data: ICharacters) => {
        this.characters = data.characters;
      }
    })
  }
}
