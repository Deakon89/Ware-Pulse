import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
}
