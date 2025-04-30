import { Component, OnInit } from '@angular/core';
import { Order, OrdersService } from '../../service/orders.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-orders',
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orderForm!: FormGroup;
  products: any[] = [];
  clients: any[] = [];
  orders: Order[] = [];

  displayedColumns: string[] = ['id', 'product', 'quantity', 'date', 'status', 'client', 'actions'];

  constructor(private ordersService: OrdersService, private fb: FormBuilder) {}

  ngOnInit() {
    this.products= [];
    this.clients= [];
    this.orderForm = this.fb.group({
      product: [null, Validators.required],
      client: [null, Validators.required],
      quantityOrdered: [1, [Validators.required, Validators.min(1)]],
      date: [new Date(), Validators.required]
    })
    this.refresh();
  }

  refresh() {
    this.ordersService.list().subscribe(list => this.orders = list);
  }

  complete(id: number) {
    this.ordersService.complete(id).subscribe(() => this.refresh());
  }

  createOrder(): void {
    if (this.orderForm.valid) {
      const formValue = this.orderForm.value;
      const newOrder = {
        id: this.orders.length ? Math.max(...this.orders.map(o => o.id)) + 1 : 1,
        product: formValue.product,
        client: formValue.client,
        quantityOrdered: formValue.quantityOrdered,
        date: formValue.date,
        status: 'NON_EVASO'
      };
      this.orders.push(newOrder as Order);
      this.orderForm.reset({ quantityOrdered: 1, date: new Date() });
    }
  }

  deleteOrder(id: number): void {
    this.orders = this.orders.filter(o => o.id !== id);
  }

}
