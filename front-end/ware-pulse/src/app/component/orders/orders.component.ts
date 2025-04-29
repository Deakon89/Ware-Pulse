import { Component, OnInit } from '@angular/core';
import { Order, OrdersService } from '../../service/orders.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-orders',
  imports: [CommonModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  displayedColumns: string[] = ['id', 'product', 'quantity', 'date', 'status', 'client', 'actions'];

  constructor(private ordersService: OrdersService) {}

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.ordersService.list().subscribe(list => this.orders = list);
  }

  complete(id: number) {
    this.ordersService.complete(id).subscribe(() => this.refresh());
  }
}
