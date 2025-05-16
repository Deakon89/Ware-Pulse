
import { Injectable, Inject, PLATFORM_ID }   from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable }   from 'rxjs';


@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
   
    const token = (typeof window !== 'undefined')
      ? localStorage.getItem('jwt')
      : null;

    
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
