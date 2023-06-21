import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import IMediaBody from '../interfaces/IMediaBody';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private baseUrl = `${environment.apiUrl}/media`;

  constructor(private http: HttpClient) { }

  public getRecent(): Observable<IMediaBody> {
    return this.http.get<IMediaBody>(`${this.baseUrl}/recent-add`, { observe: "body", responseType: 'json' });
  }

  public searchByName(mediaName: string): Observable<IMediaBody> {
    return this.http.get<IMediaBody>(`${this.baseUrl}/search-by-name/${mediaName}`, { observe: "body", responseType: 'json' });
  }
}
