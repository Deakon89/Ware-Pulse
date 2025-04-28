import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

export interface Order {
  id: number;
  productId: number;
  quantityOrdered: number;
  date?: string;
  status?: 'NON_EVASO' | 'EVASO';
  clientId: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  private path = 'orders';

  constructor(private api: ApiService) {}

  list(): Observable<Order[]> {
    return this.api.get<Order[]>(this.path);
  }

  get(id: number): Observable<Order> {
    return this.api.get<Order>(`${this.path}/${id}`);
  }

  create(order: Partial<Order>): Observable<Order> {
    return this.api.post<Order>(this.path, order);
  }

  complete(id: number): Observable<void> {
    return this.api.post<void>(`${this.path}/${id}/complete`, null);
  }

  delete(id: number): Observable<void> {
    return this.api.delete<void>(`${this.path}/${id}`);
  }
}
