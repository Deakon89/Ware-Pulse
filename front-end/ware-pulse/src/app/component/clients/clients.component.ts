// src/app/component/clients/clients.component.ts
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule }               from '@angular/common';
import { ClientService }             from '../../service/clients.service';
import { Client }                    from '../../model/clientMod';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  standalone: true,
  selector: 'app-clients',
  imports: [CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatListModule,
    MatIconModule
  ],
  templateUrl: './clients.component.html',
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  newClientName = '';
  srv = inject(ClientService);

  constructor(private clientsService: ClientService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.srv.getAll().subscribe(cl => this.clients = cl);
  }

  createClient() {
    if (!this.newClientName.trim()) return;
    const toCreate: Partial<Client> = { 'nomeAttività': this.newClientName.trim() };
    this.clientsService.create(toCreate as Client)
      .subscribe(() => {
        this.newClientName = '';
        this.load();
      });
  }

  delete(id: number) {
    this.srv.delete(id).subscribe(() => this.load());
  }
}

