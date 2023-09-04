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

  /**
   * Insere uma media na lista do usuário
   * @param body Um objeto contendo informações como: Nota do usuário, comentários sobre a media etc
   * @param token Token jwt para a autenticação 
   * @returns Em caso de sucesso o observável retorna um objeto contendo uma mensagem de sucesso, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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

  /**
   * Acha a lista de um usuário
   * @param username Nome do usuário
   * @param statusName Parâmetro opcional para buscar itens da lista do usuário com status um específico. Por exemplo, buscar só medias que o usuário concluiu ou buscar só medias que o usuário droppou
   * @returns Em caso de sucesso o observável retorna um objeto contendo um array com itens da lista do usuário, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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
