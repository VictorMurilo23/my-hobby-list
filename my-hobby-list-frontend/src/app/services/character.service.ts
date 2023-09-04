import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import ICharacters from '../interfaces/ICharacters';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CharacterService {
  private baseUrl = `${environment.apiUrl}/characters`;

  constructor(private http: HttpClient) { }

  /**
   * Busca os personagens de uma media específica
   * @param mediaName Nome da media
   * @returns Em caso de sucesso o observável retorna um objeto contendo um array com nome de personagem e papel executado pelo mesmo, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public getCharacters(mediaName: string): Observable<ICharacters> {
    return this.http.get<ICharacters>(`${this.baseUrl}/${mediaName}`, {
      observe: 'body',
      responseType: 'json',
    });
  }
}
