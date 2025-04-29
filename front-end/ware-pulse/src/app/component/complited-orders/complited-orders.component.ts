// src/app/component/completed-orders/completed-orders.component.ts
import { Component, OnInit, inject }  from '@angular/core';
import { CommonModule }                from '@angular/common';
import { ComplitedOrderService }       from '../../service/complited-order.service';
import { ComplitedOrder }              from '../../model/complited-orderMod';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';

@Component({
  standalone: true,
  selector: 'app-complited-orders',
  imports: [CommonModule, MatCardModule, MatTableModule],
  templateUrl: './complited-orders.component.html',
})
export class ComplitedOrdersComponent implements OnInit {
  orders: ComplitedOrder[] = [];
  srv = inject(ComplitedOrderService);
  displayedColumns: string[] = ['id', 'productName', 'quantityOrdered', 'date', 'client'];

  ngOnInit() {
    this.srv.getAll().subscribe(o => this.orders = o);
  }
}

