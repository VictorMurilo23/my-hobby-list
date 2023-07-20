import { Character } from "../types/Character";

interface char {
  characterRole: string;
  character: Character
}

export default interface ICharacters {
  characters: char[]
}
