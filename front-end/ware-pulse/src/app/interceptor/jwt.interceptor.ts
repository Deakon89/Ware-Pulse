
import { Injectable, Inject, PLATFORM_ID }   from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable }   from 'rxjs';

// @Injectable()
// export class JwtInterceptor implements HttpInterceptor {
//   constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     let authReq = req;

//     // solo in browser prendi il token
//     if (isPlatformBrowser(this.platformId)) {
//       const token = localStorage.getItem('jwt');
//       if (token) {
//         authReq = req.clone({
//           setHeaders: { Authorization: `Bearer ${token}` }
//         });
//       }
//     }

//     return next.handle(authReq);
//   }
@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // In contesti server-side (SSR) window non esiste
    const token = (typeof window !== 'undefined')
      ? localStorage.getItem('jwt')
      : null;

    // Se c’è un token, cloniamo la request aggiungendo l’Authorization header
    const authReq = token
      ? req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        })
      : req;

    return next.handle(authReq);
  }

}
