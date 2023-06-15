import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { environment } from '../../environments/environment.development';
import ILogin from '../interfaces/ILogin';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  reqLogin(email: string, password: string): Observable<ILogin> {
    const body = { email, password };
    return this.http.post<ILogin>(`${environment.apiUrl}/user/login`, body, { observe: "body",responseType: 'json' });
  }
}
