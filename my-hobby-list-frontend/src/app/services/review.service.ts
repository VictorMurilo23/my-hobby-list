import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { FindReviews } from '../interfaces/IReviews';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl = `${environment.apiUrl}/reviews`;

  constructor(private http: HttpClient) {}

  public findReviews(mediaId: number, page = 0): Observable<FindReviews> {
    return this.http.get<FindReviews>(`${this.baseUrl}/find/${mediaId}?page=${page}`, { observe: "body", responseType: 'json' });
  }
}
