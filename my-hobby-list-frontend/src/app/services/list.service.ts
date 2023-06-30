import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ListService {
  private baseUrl = `${environment.apiUrl}/list`;

  constructor(private http: HttpClient) { }
}
