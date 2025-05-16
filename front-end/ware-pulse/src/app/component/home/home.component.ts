import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NotificationService } from '../../service/notification.service';
import { AuthService } from '../../service/auth.service';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, RouterOutlet, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  
})
// main page
export class HomeComponent {
  show = false;
  menuActive: boolean = false;

  constructor(public svc: NotificationService, private notificationService: NotificationService,  public authService: AuthService){}

  ngOnInit() {
  if (this.authService.isLoggedIn()) {
    this.notificationService.subscribeToNotifications();
  }
}
  // prepareRoute(outlet: RouterOutlet) {
  //   return outlet?.activatedRouteData?.['animation'];
  // }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }

  closeMenu() {
    this.menuActive = false;
  }


  hasChildRoute(outlet: RouterOutlet): boolean {
    return outlet.isActivated;
  }

 isLoggedIn(): boolean {
  return !!localStorage.getItem('jwt');
}
}