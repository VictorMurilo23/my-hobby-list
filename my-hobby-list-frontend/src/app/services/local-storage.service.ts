import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  private storage: Storage;

  constructor() { 
    this.storage = window.localStorage;
  }

  /**
   * Coloca o token no localStorage
   * @param token Token jwt
   */
  setToken(token: string) {
    this.storage.setItem("token", token)
  }

  /**
   * Remove o token do localStorage
   */
  removeToken(): void {
    this.storage.removeItem("token");
  }

  /**
   * Pega o token do localStorage
   * @returns Retorna o token salvo no localStorage ou null
   */
  getToken(): string | null {
    return this.storage.getItem("token")
  }
}
