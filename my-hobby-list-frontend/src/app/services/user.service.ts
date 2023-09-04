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

  /**
   * Faz o login
   * @param email email do usuário
   * @param password senha do usuário
   * @returns Em caso de sucesso o observável retorna um objeto contendo um token jwt, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  login(email: string, password: string): Observable<ILogin> {
    const body = { email, password };
    return this.http.post<ILogin>(`${this.baseUrl}/login`, body, {
      observe: 'body',
      responseType: 'json',
    });
  }

  /**
   * Registro de usuário.
   * @param body Objeto com email, nome de usuário e senha
   * @returns Em caso de sucesso o observável retorna um objeto contendo um token jwt, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  register(body: IRegister): Observable<ILogin> {
    return this.http.post<ILogin>(`${this.baseUrl}/register`, body, {
      observe: 'body',
      responseType: 'json',
    });
  }

  /**
   * Busca as informações de perfil de um usuário.
   * @param username Nome do usuário a ser buscado
   * @returns Em caso de sucesso o observável retorna um objeto contendo nome de usuário, url da imagem de perfil, descrição do perfil do usuário e data em que o usuário se registrou, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public getProfileInfo(username: string): Observable<IProfile> {
    return this.http.get<IProfile>(`${this.baseUrl}/profile/${username}`, {
      observe: 'body',
      responseType: 'json',
    });
  }

  /**
   * Muda a imagem de perfil do usuário. Só muda com sucesso quando a imagem existe no back-end
   * @param imageUrl Url da imagem
   * @param token Token jwt para a autenticação do usuário
   * @returns Em caso de sucesso o observável retorna um objeto com uma mensagem genérica de sucesso, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
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

  /**
   * Busca e retorna um array com a url de todas as imagens de perfil disponíveis no back-end.
   * @returns Em caso de sucesso o observável retorna um objeto contendo um array com a url de todas as imagens, caso ocorra algum erro, retorna um objeto com uma chave message dizendo que erro aconteceu
   */
  public getAllProfileImagesOptions(): Observable<ProfileImages> {
    return this.http.get<ProfileImages>(
      `${environment.apiUrl}/images/profile-images`,
      {
        observe: 'body',
        responseType: 'json',
      }
    );
  }

  /**
   * Pega a username contida dentro do payload do token jwt
   * @returns Uma string com o nome do usuário ou null caso não seja possível pegar o nome do usuário
   */
  public getUsernameFromToken(): string | null {
    try {
      const token = this.localStorage.getToken();
      if (token === null) throw new Error();
      const payload = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      const { username } = payload;
      if (username === null) throw new Error();
      return username;
    } catch (e) {
      this.localStorage.removeToken();
      return null;
    }
  }

  /**
   * Realiza logout, removendo o token do localStorage e redirecionando pra página de login
   */
  public logout(): void {
    this.localStorage.removeToken();
    this.router.navigate(["/login"]);
  }
}
