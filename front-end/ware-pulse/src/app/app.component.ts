import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {trigger, transition, style, animate, query, group} from '@angular/animations';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  animations: [
    trigger('fadeAnimation', [
      // ogni volta che entra un nuovo component…
      transition('* <=> *', [
        // impila i due elementi (vecchio e nuovo)
        query(':enter, :leave', [
          style({
            position: 'absolute',
            width: '100%',
            opacity: 0
          })
        ], { optional: true }),

        // anima contemporaneamente
        group([
          query(':leave', [
            animate('300ms ease', style({ opacity: 0 }))
          ], { optional: true }),
          query(':enter', [
            animate('300ms ease', style({ opacity: 1 }))
          ], { optional: true })
        ])
      ])
    ])
  ]
})
export class AppComponent {
  title = 'ware-pulse';

  prepareRoute(outlet: any) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }
}
