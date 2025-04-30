import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(private router: Router) {}
  
  // example data
  user = {
    username: 'Admin',
    bio: 'Frontend Developer | Angular Enthusiast 🚀',
    email: 'Start2Impact@example.com',
    location: 'Rieti, Italia',
    avatarUrl: 'https://i.pravatar.cc/300' 
  };

  goToDash() {
    window.location.href = '/dashboard';
  }

  logout() {
    // this.authService.logout();
    this.router.navigate(['/']); 
  }
}
