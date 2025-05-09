import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from './orders.service';
import { Product } from './products.service';


@Injectable({providedIn:'root'})
export class DashboardService {
  constructor(private http: HttpClient) {}
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/dashboard/orders');
  }
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>('/api/dashboard/products');
  }
}
