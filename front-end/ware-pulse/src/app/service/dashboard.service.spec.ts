import { TestBed } from '@angular/core/testing';
import { DashboardService } from './dashboard.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Order } from './orders.service';
import { Product } from './products.service';

describe('DashboardService', () => {
  let service: DashboardService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DashboardService]
    });

    service = TestBed.inject(DashboardService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch orders', () => {
    const mockOrders: Order[] = [
      { id: 1, quantityOrdered: 2, product: { id: 1, name: 'Prodotto' }, client: { id: 1, nomeAttività: 'Cliente' }, date: new Date().toISOString(), status: 'NON_EVASO' }
    ];

    service.getOrders().subscribe(data => {
      expect(data).toEqual(mockOrders);
    });

    const req = httpMock.expectOne('/api/dashboard/orders');
    expect(req.request.method).toBe('GET');
    req.flush(mockOrders);
  });

  it('should fetch products', () => {
    const mockProducts: Product[] = [
      { id: 1, name: 'Prodotto', quantity: 5, price: 10 }
    ];

    service.getProducts().subscribe(data => {
      expect(data).toEqual(mockProducts);
    });

    const req = httpMock.expectOne('/api/dashboard/products');
    expect(req.request.method).toBe('GET');
    req.flush(mockProducts);
  });

  it('should fetch clients', () => {
    const mockClients = [{ id: 1, nomeAttività: 'Cliente1' }];

    service.getClient().subscribe(data => {
      expect(data).toEqual(mockClients);
    });

    const req = httpMock.expectOne('/api/dashboard/clients');
    expect(req.request.method).toBe('GET');
    req.flush(mockClients);
  });

  it('should fetch completed orders', () => {
    const mockCompletedOrders = [{ id: 1, productName: 'Prodotto', quantityOrdered: 3 }];

    service.getCompletedOrders().subscribe(data => {
      expect(data).toEqual(mockCompletedOrders);
    });

    const req = httpMock.expectOne('/api/dashboard/completed-orders');
    expect(req.request.method).toBe('GET');
    req.flush(mockCompletedOrders);
  });
});

