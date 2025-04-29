// src/app/service/completed-order.service.ts
import { Injectable }         from '@angular/core';
import { HttpClient }         from '@angular/common/http';
import { Observable }         from 'rxjs';
import { ComplitedOrder }     from '../model/complited-orderMod';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ComplitedOrderService {
  private readonly url = 'completed-orders';

  constructor(private http: HttpClient, private api: ApiService) {}

  getAll(): Observable<ComplitedOrder[]> {
    return this.api.get<ComplitedOrder[]>(this.url);
  }
}
