import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description?: string;
  quantity: number;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private path = 'dashboard/products';

  constructor(private api: ApiService) {}

  list(): Observable<Product[]> {
    return this.api.get<Product[]>(this.path);
  }

  get(id: number): Observable<Product> {
    return this.api.get<Product>(`${this.path}/${id}`);
  }

  create(product: Partial<Product>): Observable<Product> {
    return this.api.post<Product>(this.path, product);
  }

  update(id: number, product: Partial<Product>): Observable<Product> {
    return this.api.put<Product>(`${this.path}/${id}`, product);
  }

  delete(id: number): Observable<void> {
    return this.api.delete<void>(`${this.path}/${id}`);
  }
}

