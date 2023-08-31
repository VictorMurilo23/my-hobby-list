import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CreateReview, FindReviews, FindUserReviews, Review } from '../interfaces/IReviews';
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

  public createReview(reviewObj: CreateReview, token: string) {
    const headers = new HttpHeaders({
      Authorization: token,
    });
    const { content, mediaId, recommended } = reviewObj;
    return this.http.post(`${this.baseUrl}/create`, { content, mediaId, recommended }, {
      observe: 'body',
      responseType: 'json',
      headers,
    });
  }

  public findUserReview(mediaId: number, token: string) {
    const headers = new HttpHeaders({
      Authorization: token,
    });
    return this.http.get<Review>(`${this.baseUrl}/find-user-review/${mediaId}`, {
      observe: 'body',
      responseType: 'json',
      headers,
    });
  }

  public findAllUserReviews(username: string, page: number): Observable<FindUserReviews> {
    return this.http.get<FindUserReviews>(`${this.baseUrl}/find-all-user-reviews/${username}?page=${page}`, {
      observe: 'body',
      responseType: 'json',
    });
  }

  public editReview(reviewObj: CreateReview, token: string) {
    const headers = new HttpHeaders({
      Authorization: token,
    });
    const { content, mediaId, recommended } = reviewObj;
    return this.http.patch(`${this.baseUrl}/edit`, { content, mediaId, recommended }, {
      observe: 'body',
      responseType: 'json',
      headers,
    })
  }
}
