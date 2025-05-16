import { TestBed } from '@angular/core/testing';
import { NotificationService } from './notification.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

describe('NotificationService', () => {
  let service: NotificationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        NotificationService,
        { provide: PLATFORM_ID, useValue: 'browser' }
      ]
    });

    service = TestBed.inject(NotificationService);
    httpMock = TestBed.inject(HttpTestingController);

    // Intercetta la richiesta iniziale
    httpMock.expectOne('http://localhost:8080/api/notifications')
      .flush([]); // oppure [fakeNotification] se vuoi testare contenuti
  });

  afterEach(() => {
    httpMock.verify(); // Assicura che non restino richieste pendenti
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should clear notifications and close EventSource', () => {
    service.clear();
    expect(service['eventSource']).toBeUndefined();
  });

  it('should delete a notification and update the list', () => {
    service.notifications.set([{ id: 1, message: 'Test', timestamp: new Date().toISOString() }]);

    service.delete(1).subscribe();
    const req = httpMock.expectOne('http://localhost:8080/api/notifications/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(service.notifications().length).toBe(0);
  });
});
