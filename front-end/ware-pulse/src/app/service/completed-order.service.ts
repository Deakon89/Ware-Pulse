// src/app/service/completed-order.service.ts
import { Injectable }         from '@angular/core';
import { HttpClient }         from '@angular/common/http';
import { Observable }         from 'rxjs';
import { CompletedOrder }     from '../model/completed-orderMod';
import { ApiService } from './api.service';


@Injectable({ providedIn: 'root' })
export class CompletedOrdersService {
  private path = 'dashboard/completed-orders';
  constructor(private api: ApiService) {}

  list(): Observable<CompletedOrder[]> {
    return this.api.get<CompletedOrder[]>(this.path);
  }
  get(id: number): Observable<CompletedOrder> {
    return this.api.get<CompletedOrder>(`${this.path}/${id}`);
  }
  create(co: Partial<CompletedOrder>): Observable<CompletedOrder> {
    return this.api.post<CompletedOrder>(this.path, co);
  }
  update(id: number, co: Partial<CompletedOrder>): Observable<CompletedOrder> {
    return this.api.put<CompletedOrder>(`${this.path}/${id}`, co);
  }
  delete(id: number): Observable<void> {
    return this.api.delete<void>(`${this.path}/${id}`);
  }
}


