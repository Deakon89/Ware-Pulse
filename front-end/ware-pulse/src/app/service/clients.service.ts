// src/app/service/client.service.ts
import { Injectable }     from '@angular/core';
import { HttpClient }     from '@angular/common/http';
import { Observable }     from 'rxjs';
import { Client }         from '../model/clientMod';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private readonly url = '/api/clients';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.url);
  }

  create(client: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(this.url, client);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}


