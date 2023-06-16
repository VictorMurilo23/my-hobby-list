import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import IMedia from '../interfaces/IMedia';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private baseUrl: string = `${environment.apiUrl}/media`;

  constructor(private http: HttpClient) { }

  public getRecent(): Observable<IMedia[]> {
    return this.http.get<IMedia[]>(`${this.baseUrl}/recent-add`, { observe: "body", responseType: 'json' });
  }
}
