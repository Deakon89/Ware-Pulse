import { Component } from '@angular/core';
import { NotificationService } from '../../service/notification.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notifications',
  imports: [CommonModule],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent {
  constructor(public svc: NotificationService) {}
  onDelete(id: number) {
    this.svc.delete(id).subscribe();
  }
}
