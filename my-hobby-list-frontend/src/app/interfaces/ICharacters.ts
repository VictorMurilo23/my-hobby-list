import { Character } from "../types/Character";

export interface char {
  characterRole: string;
  character: Character
}

export default interface ICharacters {
  characters: char[]
}
