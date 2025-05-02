// src/app/service/notification.service.ts
// import { Injectable } from '@angular/core';
// import { MatSnackBar } from '@angular/material/snack-bar'; // opzionale, per toast in-app

// @Injectable({ providedIn: 'root' })
// export class NotificationService {
//   constructor(private snackBar: MatSnackBar) {}

//   // richiede il permesso di mostrare notifiche di sistema
//   requestPermission(): Promise<NotificationPermission> {
//     return Notification.requestPermission();
//   }

//   // mostra una notifica di sistema (browser)
//   showBrowserNotification(title: string, options?: NotificationOptions) {
//     if (Notification.permission === 'granted') {
//       new Notification(title, options);
//     }
//   }

//   // mostra un toast in-app con Angular Material
//   showToast(message: string, action = 'OK', duration = 3000) {
//     this.snackBar.open(message, action, { duration });
//   }
// }
// src/app/services/notification.service.ts
import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Notification } from '../model/notification';
import { tap } from 'rxjs';
// import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private api = `http://localhost:8080/api/notifications`;
  // signal (Angular 19) o BehaviorSubject
  public notifications = signal<Notification[]>([]);

  constructor(private http: HttpClient) {
    // 1) carica esistenti
    this.http.get<Notification[]>(this.api).subscribe(list => {
      this.notifications.set(list);
    });
    // 2) sottoscrivi SSE
    const es = new EventSource(`${this.api}/stream`);
    es.onmessage = ({ data }) => {
      const n: Notification = JSON.parse(data);
      // aggiungi in testa
      this.notifications.update(curr => [n, ...curr]);
    };
  }

  delete(id: number) {
    return this.http.delete(`${this.api}/${id}`).pipe(tap(() => {
      this.notifications.update(curr => curr.filter(n => n.id !== id));
    }));
  }
}

