// src/app/component/completed-orders/completed-orders.component.ts
import { Component, OnInit, inject }  from '@angular/core';
import { CommonModule }                from '@angular/common';
import { CompletedOrdersService }       from '../../service/completed-order.service';
import { CompletedOrder }              from '../../model/completed-orderMod';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';

@Component({
  standalone: true,
  selector: 'app-complited-orders',
  imports: [CommonModule, MatCardModule, MatTableModule],
  templateUrl: './completed-orders.component.html',
})
export class CompletedOrdersComponent implements OnInit {
  orders: CompletedOrder[] = [];
  srv = inject(CompletedOrdersService);
  displayedColumns: string[] = ['id', 'productName', 'quantityOrdered', 'date', 'client'];

  ngOnInit() {
    this.srv.list().subscribe(o => this.orders = o);
  }
}

