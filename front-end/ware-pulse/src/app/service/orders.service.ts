import { Injectable } from '@angular/core';
import { ApiService }  from './api.service';
import { Observable }  from 'rxjs';

export interface Order {
  id: number;
  product: { id: number; name: string; };
  quantityOrdered: number;
  date: string;
  status: string;
  client: { id: number; nomeAttività: string; };
}

@Injectable({ providedIn: 'root' })
export class OrdersService {
  private path = 'dashboard/orders';
  constructor(private api: ApiService) {}

  list(): Observable<Order[]> {
    return this.api.get<Order[]>(this.path);
  }
  get(id: number): Observable<Order> {
    return this.api.get<Order>(`${this.path}/${id}`);
  }
  create(o: Partial<Order>): Observable<Order> {
    return this.api.post<Order>(this.path, o);
  }
  update(id: number, o: Partial<Order>): Observable<Order> {
    return this.api.put<Order>(`${this.path}/${id}`, o);
  }
  delete(id: number): Observable<void> {
    return this.api.delete<void>(`${this.path}/${id}`);
  }

  complete(id: number): Observable<Order> {
  return this.api.put<Order>(`${this.path}/${id}/complete`, {});
}

}
