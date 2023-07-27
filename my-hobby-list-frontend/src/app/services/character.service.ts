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

  public getCharacters(mediaName: string): Observable<ICharacters> {
    return this.http.get<ICharacters>(`${this.baseUrl}/${mediaName}`, {
      observe: 'body',
      responseType: 'json',
    });
  }
}
