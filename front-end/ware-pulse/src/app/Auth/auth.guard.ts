// src/app/guards/auth.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { AuthService } from '../service/auth.service';

export const authGuard: CanActivateFn = (_route, _state): boolean | UrlTree => {
  const auth = inject(AuthService);
  const router = inject(Router);

 
  if (auth.isLoggedIn()) return true;
  const token = localStorage.getItem('jwt');
  if (token) {
    return true;
  }

  
  return router.createUrlTree(['/login']);
};
