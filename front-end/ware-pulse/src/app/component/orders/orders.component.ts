import { Component, OnInit } from '@angular/core';
import { Order, OrdersService } from '../../service/orders.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-orders',
  imports: [CommonModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];

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
