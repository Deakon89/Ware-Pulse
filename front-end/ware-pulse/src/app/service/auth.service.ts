import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profile } from '../model/profile';

export interface RegisterPayload {
  username: string;
  password: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(payload: RegisterPayload): Observable<{ id: number, username: string, email: string }> {
    return this.http.post<{ id: number, username: string, email: string }>(
      `${this.apiUrl}/register`,
      payload
    );
  }

  login(payload: { username:string, password:string }): Observable<{token:string}> {
    return this.http.post<{token:string}>(`${this.apiUrl}/login`, payload);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwt');
  }

  getProfile(): Observable<Profile> {
    return this.http.get<Profile>(`${this.apiUrl}/me`);
  }

  logout() {
    localStorage.removeItem('jwt');
  
}
}