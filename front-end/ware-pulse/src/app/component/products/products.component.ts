import { Component, OnInit } from '@angular/core';
import { ProductsService, Product } from '../../service/products.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-products',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule 
  ],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  form!: FormGroup;
  editing: boolean = false;
  editId: number | null = null;

  constructor(
    private productsService: ProductsService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadAll();
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

