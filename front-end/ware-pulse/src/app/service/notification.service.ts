import { Injectable, Inject, PLATFORM_ID, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Notification } from '../model/notification';
import { Subject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private api = `https://ware-pulse-1fa9b002251d.herokuapp.com/api/notifications`;
  public notifications = signal<Notification[]>([]);
  private newNotificationSubject = new Subject<Notification>();
  public newNotification$ = this.newNotificationSubject.asObservable();

  
  private eventSource?: EventSource;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    
    this.http.get<Notification[]>(this.api)
      .subscribe(list => this.notifications.set(list));

    
    if (isPlatformBrowser(this.platformId)) {
      this.eventSource = new EventSource(`${this.api}/stream`);
      this.eventSource.onmessage = ({ data }) => {
        const n: Notification = JSON.parse(data);
        this.notifications.update(curr => [n, ...curr]);
      };
    }
  }

  delete(id: number) {
    return this.http.delete(`${this.api}/${id}`).pipe(
      tap(() => {
        this.notifications.update(curr => curr.filter(n => n.id !== id));
      })
    );
  }

  
  destroy() {
    this.eventSource?.close();
  }

  subscribeToNotifications() {
  if (!this.eventSource) {
    this.eventSource = new EventSource(`${this.api}/stream`);
    this.eventSource.onmessage = (event) => {
      const notifica = JSON.parse(event.data);
      this.notifications.update(curr => [notifica, ...curr]); 
      this.newNotificationSubject.next(notifica); 
    };
  }
}

clear() {
  this.notifications.set([]); 
  if (this.eventSource) {
    this.eventSource.close(); 
    this.eventSource = undefined;
  }
}
}





