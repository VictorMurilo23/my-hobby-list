import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import IMedia from '../interfaces/IMedia';
import IMediaCardsBody from '../interfaces/IMediaCardsBody';
@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private baseUrl = `${environment.apiUrl}/media`;
  private mediaName = "";

  constructor(private http: HttpClient) { 

    console.log(this.baseUrl);
  }

  public getRecent(): Observable<IMediaCardsBody> {
    return this.http.get<IMediaCardsBody>(`${this.baseUrl}/recent-add`, { observe: "body", responseType: 'json' });
  }

  public searchByName(mediaName: string): Observable<IMediaCardsBody> {
    return this.http.get<IMediaCardsBody>(`${this.baseUrl}/search-by-name/${mediaName}`, { observe: "body", responseType: 'json' });
  }

  public getMediaById(mediaId: string): Observable<IMedia> {
    return this.http.get<IMedia>(`${this.baseUrl}/search-by-id/${mediaId}`, { observe: "body", responseType: 'json' });
  }

  public getMediaName(): string {
    return this.mediaName;
  }

  public setMediaName(name: string) {
    this.mediaName = name;
  }
}
