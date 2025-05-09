import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule, RouterOutlet } from '@angular/router';
import { DashboardService } from '../../service/dashboard.service';
import { Product } from '../../service/products.service';
import { Order } from '../../service/orders.service';



@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    RouterOutlet,
    RouterModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  products: Product[] = [];
  orders: Order[] = [];
  constructor(private dash: DashboardService){}
  ngOnInit(){
    this.dash.getProducts().subscribe(ps => this.products = ps);
    this.dash.getOrders().subscribe(os => this.orders = os);
  }
}

