import { Injectable } from '@angular/core';
import ILogin from '../interfaces/ILogin';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import IRegister from '../interfaces/IRegister';
import IProfile from '../interfaces/IProfile';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = `${environment.apiUrl}/user`;
  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<ILogin> {
    const body = { email, password };
    return this.http.post<ILogin>(`${this.baseUrl}/login`, body, { observe: "body", responseType: 'json' });
  }

  register(body: IRegister): Observable<ILogin> {
    return this.http.post<ILogin>(`${this.baseUrl}/register`, body, { observe: "body", responseType: 'json' });
  }

  public getProfileInfo(username: string): Observable<IProfile> {
    return this.http.get<IProfile>(`${this.baseUrl}/user/profile/${username}`, { observe: "body", responseType: 'json' });
  }
}
