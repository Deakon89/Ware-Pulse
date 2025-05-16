// src/app/component/dashboard/dashboard.component.ts
import { Component, OnInit, ViewChild }          from '@angular/core';
import { CommonModule }               from '@angular/common';
import { MatCardModule }              from '@angular/material/card';
import { MatListModule }              from '@angular/material/list';
import { MatButtonModule }            from '@angular/material/button';
import { switchMap }                  from 'rxjs/operators';
import { Client }                     from '../../model/clientMod';
import { ClientService}      from '../../service/clients.service';
import { ProductsService, Product }   from '../../service/products.service';
import { OrdersService, Order }       from '../../service/orders.service';
import { CompletedOrdersService} from '../../service/completed-order.service';
import { CompletedOrder } from '../../model/completed-orderMod';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatMenuModule } from '@angular/material/menu';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    RouterOutlet,
    RouterModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
    MatMenuTrigger,
    MatMenuModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  clients: Client[] = [];
  products: Product[] = [];
  orders: Order[] = [];
  completedOrders: CompletedOrder[] = [];
  @ViewChild('menuTrigger') menuTrigger!: MatMenuTrigger;

  constructor(
    private clientService: ClientService,
    private productsService: ProductsService,
    private ordersService: OrdersService,
    private completedOrdersService: CompletedOrdersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
    this.loadProducts();
    this.loadOrders();
    this.loadCompletedOrders();
  }

  loadClients() {
    this.clientService.getAll()
      .subscribe(cl => this.clients = cl);
  }

  loadProducts() {
    this.productsService.list()
      .subscribe(p => this.products = p);
  }

  loadOrders() {
    this.ordersService.list()
      .subscribe(o => this.orders = o);
  }

  loadCompletedOrders() {
    this.completedOrdersService.list()
      .subscribe((c: CompletedOrder[]) => this.completedOrders = c);
  }

  deleteClient(id: number) {
    this.clientService.delete(id)
      .subscribe(() => this.loadClients());
  }

  deleteProduct(id: number) {
    this.productsService.delete(id)
      .subscribe(() => this.loadProducts());
  }

  deleteOrder(id: number) {
    this.ordersService.delete(id)
      .subscribe(() => this.loadOrders());
  }

  completeOrder(id: number) {
    const o = this.orders.find(x => x.id === id);
    if (!o) return;

    const co: Partial<CompletedOrder> = {
      productId:    o.product.id,
      productName:  o.product.name,
      quantityOrdered: o.quantityOrdered,
      date:         o.date,
      client:       o.client
    };

   
    this.ordersService.delete(id).pipe(
      switchMap(() => this.completedOrdersService.create(co))
    ).subscribe(() => {
      this.loadOrders();
      this.loadCompletedOrders();
    });
  }

  deleteCompletedOrder(id: number) {
    this.completedOrdersService.delete(id)
      .subscribe(() => this.loadCompletedOrders());
  }

   navigateAndClose(route: string) {
    this.menuTrigger.closeMenu();
  }

}

