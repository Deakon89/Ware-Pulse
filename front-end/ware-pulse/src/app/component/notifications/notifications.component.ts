import { Component } from '@angular/core';
import { NotificationService } from '../../service/notification.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-notifications',
  imports: [ 
    CommonModule,
    MatCardModule,
    MatListModule,
    MatIconModule
  ],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent {
  constructor(public svc: NotificationService) {}
  onDelete(id: number) {
    this.svc.delete(id);
  }
}
