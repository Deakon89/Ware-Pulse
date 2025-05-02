import { Component, OnInit } from '@angular/core';
import { ProductsService, Product } from '../../service/products.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NotificationService } from '../../service/notification.service';
import { interval, switchMap } from 'rxjs';

@Component({
  selector: 'app-products',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatSnackBarModule
  ],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  private notified = new Set<number>();
  form!: FormGroup;
  editing: boolean = false;
  editId: number | null = null;
  displayedColumns: string[] = ['id', 'name', 'quantity', 'price', 'actions'];

  constructor(
    private productsService: ProductsService,
    private fb: FormBuilder,
    private notifSvc: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    // this.notifSvc.requestPermission();
    // interval(30_000).pipe(
    //   switchMap(() => this.productsService.list())
    // ).subscribe(products => {
    //   products
    //     .filter(p => p.quantity >= 5 && !this.notified.has(p.id))
    //     .forEach(p => {
    //       this.notifSvc.showBrowserNotification(
    //         'Scorta alta',
    //         { body: `${p.name} ha ${p.quantity} unità in magazzino` }
    //       );
    //       this.notifSvc.showToast(`Scorta di ${p.name} ≥ 5`);
    //       this.notified.add(p.id);
    //     });
    // });
    // inizializza form per create/update
    this.form = this.fb.group({
      name: [''],
      description: [''],
      price: [0],
      quantity: [0]
    });
  }

  loadAll(): void {
    this.productsService.list()
      .subscribe(res => this.products = res);
  }

  startCreate(): void {
    this.editing = false;
    this.editId = null;
    this.form.reset({ name: '', description: '', price: 0, quantity: 0 });
  }

  startEdit(p: Product): void {
    this.editing = true;
    this.editId = p.id;
    this.form.setValue({
      name: p.name,
      description: p.description || '',
      price: p.price,
      quantity: p.quantity
    });
  }

  submit(): void {
    const data = this.form.value;
    if (this.editing && this.editId != null) {
      this.productsService.update(this.editId, data)
        .subscribe(() => {
          this.loadAll();
          this.editing = false;
          this.editId = null;
        });
    } else {
      this.productsService.create(data)
        .subscribe(() => {
          this.loadAll();
        });
    }
  }

  delete(id: number): void {
    if (!confirm('Sei sicuro di voler eliminare questo prodotto?')) return;
    this.productsService.delete(id)
      .subscribe(() => this.loadAll());
  }
}


