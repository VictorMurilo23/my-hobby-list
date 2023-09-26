import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { HttpClientModule } from '@angular/common/http';
import { LocalStorageService } from './local-storage.service';

describe('UserService', () => {
  let service: UserService;
  let localStorage: LocalStorageService;

  /**
   * payload: { username: Nome de usuário }
   */
  const tokenWithUsername = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ik5vbWUgZGUgdXN1w6FyaW8ifQ.5FZM-09Xno5eRoq-RpiU4EfO3Dp3Qap7p4TWxCK2ajc";

  /**
   * payload: {
   * "sub": "1234567890",
   * "name": "John Doe",
   * "iat": 1516239022 
   * }
   */
  const tokenWithoutUsername = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    localStorage = TestBed.inject(LocalStorageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return username from jwt token', () =>  {
    spyOn(localStorage, "getToken").and.returnValue(tokenWithUsername)
    const username = service.getUsernameFromToken();
    expect(username).toBe("Nome de usuário")
  });

  it('should return null if jwt token doesnt have a username in payload', () => {
    spyOn(localStorage, "getToken").and.returnValue(tokenWithoutUsername);
    spyOn(localStorage, "removeToken");
    const username = service.getUsernameFromToken();
    expect(username).toBe(null);
  });

  it('should return null if token is invalid', () => {
    spyOn(localStorage, "getToken").and.returnValue("invalid");
    spyOn(localStorage, "removeToken");
    const username = service.getUsernameFromToken();
    expect(username).toBe(null);
  });
});
