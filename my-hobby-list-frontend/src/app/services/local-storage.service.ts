import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  private storage: Storage;

  constructor() { 
    this.storage = window.localStorage;
  }

  setToken(token: string) {
    this.storage.setItem("token", token)
  }

  getToken(): string | null {
    return this.storage.getItem("token")
  }
}
