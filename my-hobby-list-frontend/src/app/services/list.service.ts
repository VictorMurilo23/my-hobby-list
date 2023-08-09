import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import IInsertInfo from '../types/IInsertInfo';
import { Observable } from 'rxjs';
import IMessage from '../interfaces/IMessage';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import IUserListBody from '../interfaces/IUserListBody';

@Injectable({
  providedIn: 'root',
})
export class ListService {
  private baseUrl = `${environment.apiUrl}/list`;

  constructor(private http: HttpClient) {}

  public insertItem(body: IInsertInfo, token: string): Observable<IMessage> {
    const headers = new HttpHeaders({
      Authorization: token,
    });
    return this.http.post<IMessage>(`${this.baseUrl}/insert`, body, {
      observe: 'body',
      responseType: 'json',
      headers,
    });
  }

  public findList(username: string, statusName?: string | undefined): Observable<IUserListBody> {
    if (statusName === undefined) {
      return this.http.get<IUserListBody>(`${this.baseUrl}/find/${username}`, {
        observe: 'body',
        responseType: 'json',
      });
    }
    return this.http.get<IUserListBody>(`${this.baseUrl}/find/${username}?statusName=${statusName}`, {
      observe: 'body',
      responseType: 'json',
    });
  }
}
