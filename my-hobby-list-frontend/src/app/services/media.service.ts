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
  }

  /**
   * Encontra as 10 últimas medias recém adicionadas.
   * @returns Em caso de sucesso o observável retorna um objeto contendo um array com url da capa da media, nome da media e id da media, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public getRecent(): Observable<IMediaCardsBody> {
    return this.http.get<IMediaCardsBody>(`${this.baseUrl}/recent-add`, { observe: "body", responseType: 'json' });
  }

  /**
   * Busca medias por nome.
   * @param mediaName Nome da media
   * @returns Em caso de sucesso o observável retorna um objeto contendo um array com todas as media que incluem o paramêtro mediaName em seu nome, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public searchByName(mediaName: string): Observable<IMediaCardsBody> {
    return this.http.get<IMediaCardsBody>(`${this.baseUrl}/search-by-name/${mediaName}`, { observe: "body", responseType: 'json' });
  }

  /**
   * Busca uma única media por id
   * @param mediaId Id da media utilizado na busca
   * @returns Em caso de sucesso o observável retorna um objeto contendo as informaçãoes básicas da media, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public getMediaById(mediaId: string): Observable<IMedia> {
    return this.http.get<IMedia>(`${this.baseUrl}/search-by-id/${mediaId}`, { observe: "body", responseType: 'json' });
  }

  /**
   * Pega o nome de uma media salvada anteriormente.
   * @returns Retorna o nome de uma media
   */
  public getMediaName(): string {
    return this.mediaName;
  }

  /**
   * Serve salvar o nome de uma media específica.
   * @param name Nome da media
   */
  public setMediaName(name: string) {
    this.mediaName = name;
  }
}
