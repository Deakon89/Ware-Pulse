
import { Injectable, Inject, PLATFORM_ID }   from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable }   from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  // intercept(req: HttpRequest<any>, next: HttpHandler)
  //   : Observable<HttpEvent<any>> {
  //   const token = localStorage.getItem('jwt');
  //   if (token) {
  //     req = req.clone({
  //       setHeaders: { Authorization: `Bearer ${token}` }
  //     });
  //   }
  //   return next.handle(req);
  // }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;

    // solo in browser prendi il token
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      if (token) {
        authReq = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` }
        });
      }
    }

    return next.handle(authReq);
  }

}
