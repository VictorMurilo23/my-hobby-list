import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import IMedia from '../interfaces/IMedia';
import { Observable } from 'rxjs';
import IRecentAdd from '../interfaces/IRecentAdd';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private baseUrl: string = `${environment.apiUrl}/media`;

  constructor(private http: HttpClient) { }

  public getRecent(): Observable<IRecentAdd> {
    return this.http.get<IRecentAdd>(`${this.baseUrl}/recent-add`, { observe: "body", responseType: 'json' });
  }
}
