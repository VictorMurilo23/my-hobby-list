import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CreateReview, FindReviews, FindUserReviews, Review, ReviewDetails } from '../interfaces/IReviews';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl = `${environment.apiUrl}/reviews`;

  constructor(private http: HttpClient) {}

  /**
   * Encontra reviews de uma media específica. O retorno das reviews funciona no esquema de páginas, cada página contendo no máximo 10 reviews. O funcionamento das páginas é parecido com o de um array, ou seja, a página 0 é a primeira página.
   * @param mediaId Id da media a ser utilizado na busca
   * @param page Número da página a ser buscada
   * @returns Em caso de sucesso o observável retorna um objeto contendo o número total de páginas e um array contendo no máximo 10 reviews, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public findReviews(mediaId: number, page = 0): Observable<FindReviews> {
    return this.http.get<FindReviews>(`${this.baseUrl}/find/${mediaId}?page=${page}`, { observe: "body", responseType: 'json' });
  }

  /**
   * Cria uma review 
   * @param reviewObj Objeto contendo id da media, conteúdo da review e se o usuário recomenda ou não a media
   * @param token Token jwt utilizado na autenticação do usuário
   * @returns Em caso de sucesso o observável retorna um objeto contendo uma mensagem genérica de sucesso, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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

  /**
   * Acha uma review de um usuário específico. Utilizado na rota media/review só para mostrar a review feita pelo usuário sobre uma media específica.
   * @param mediaId Id da media
   * @param token Token jwt para a autenticação do usuário
   * @returns Em caso de sucesso o observável retorna um objeto contendo a review feita pelo usuário, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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

  /**
   * Acha todas as reviews feitas por um usuário específico. Funciona em esquema de páginas, em que cada página contém no máximo 10 reviews
   * @param username Nome do usuário
   * @param page Número da página, lembre-se que a primeira página é de número 0.
   * @returns Em caso de sucesso o observável retorna um objeto contendo o número total de páginas e um array contendo no máximo 10 reviews, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public findAllUserReviews(username: string, page: number): Observable<FindUserReviews> {
    return this.http.get<FindUserReviews>(`${this.baseUrl}/find-all-user-reviews/${username}?page=${page}`, {
      observe: 'body',
      responseType: 'json',
    });
  }

  /**
   * Editar review
   * @param reviewObj Objeto contendo o conteúdo da review, mediaId e se o usuário recomenda ou não a media
   * @param token Token jwt usado pra autenticação
   * @returns Em caso de sucesso o observável retorna um objeto contendo uma mensagem dizendo que foi um sucesso, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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

  /**
   * Busca uma review e seus comentários
   * @param username Nome do usuário que fez a review
   * @param mediaId Id da media referente a review
   * @returns Um objeto contendo os comentários e a review
   */
  public reviewDetails(username: string, mediaId: string) {
    return this.http.get<ReviewDetails>(`${environment.apiUrl}/review-comments/find/${username}/${mediaId}`, { observe: "body", responseType: 'json' });
  }
}
