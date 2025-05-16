import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { Profile } from '../../model/profile';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    CommonModule
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user?: Profile;
  constructor(private router: Router, private authService: AuthService, private notificationService: NotificationService) {}
  ngOnInit(): void {
    this.authService.getProfile().subscribe({
      next: profile => this.user = profile,
      error: ()      => this.router.navigate(['/login'])
    });
  }
  
  

  goToDash() {
    this.router.navigate(['/dashboard']);
  }

  logout() {
    this.authService.logout();
    this.notificationService.clear();
    this.router.navigate(['/']); 
  }
}
