import { Injectable } from '@angular/core';
import ILogin from '../interfaces/ILogin';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import IRegister from '../interfaces/IRegister';
import IProfile from '../interfaces/IProfile';
import ProfileImages from '../types/ProfileImages';
import { Router } from '@angular/router';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = `${environment.apiUrl}/user`;
  constructor(private http: HttpClient, private localStorage: LocalStorageService, private router: Router) {}

  login(email: string, password: string): Observable<ILogin> {
    const body = { email, password };
    return this.http.post<ILogin>(`${this.baseUrl}/login`, body, {
      observe: 'body',
      responseType: 'json',
    });
  }

  register(body: IRegister): Observable<ILogin> {
    return this.http.post<ILogin>(`${this.baseUrl}/register`, body, {
      observe: 'body',
      responseType: 'json',
    });
  }

  public getProfileInfo(username: string): Observable<IProfile> {
    return this.http.get<IProfile>(`${this.baseUrl}/profile/${username}`, {
      observe: 'body',
      responseType: 'json',
    });
  }

  public changeProfileImage(imageUrl: string, token: string) {
    const headers = new HttpHeaders({
      Authorization: token,
    });
    return this.http.patch<IProfile>(
      `${this.baseUrl}/profile/change-profile-image`,
      { imageUrl },
      { observe: 'body', responseType: 'json', headers }
    );
  }

  public getAllProfileImagesOptions(): Observable<ProfileImages> {
    return this.http.get<ProfileImages>(
      `${environment.apiUrl}/images/profile-images`,
      {
        observe: 'body',
        responseType: 'json',
      }
    );
  }

  public logout(): void {
    this.localStorage.removeToken();
    this.router.navigate(["/login"]);
  }
}
