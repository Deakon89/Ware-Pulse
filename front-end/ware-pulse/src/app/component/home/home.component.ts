import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
// import { routeAnimations } from '../../route-animation';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, RouterOutlet, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  // animations: [routeAnimations],
  template: `
  //   <div [@routeAnimations]="prepareRoute(outlet)">
  //     <router-outlet #outlet="outlet"></router-outlet>
  //   </div>
  // `
})
// main page
export class HomeComponent {
 
  menuActive: boolean = false;
  prepareRoute(outlet: RouterOutlet) {
    return outlet?.activatedRouteData?.['animation'];
  }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }

  closeMenu() {
    this.menuActive = false;
  }


  hasChildRoute(outlet: RouterOutlet): boolean {
    return outlet.isActivated;
  }
}