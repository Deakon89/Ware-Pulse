import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
// import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Order } from './orders.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private base = "http://localhost:8080/api";

  constructor(private http: HttpClient) {}

  get<T>(path: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(`${this.base}/${path}`, { params });
  }

  post<T>(path: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.base}/${path}`, body);
  }

  put<T>(path: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.base}/${path}`, body);
  }

  delete<T>(path: string): Observable<T> {
    return this.http.delete<T>(`${this.base}/${path}`);
  }
  complete(id: number): Observable<Order> {
  return this.http.post<Order>(`${this.base}/${id}/complete`, {});
}
}
