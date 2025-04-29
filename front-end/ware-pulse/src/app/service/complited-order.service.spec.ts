import { TestBed } from '@angular/core/testing';

import { ComplitedOrderService } from './complited-order.service';

describe('ComplitedOrderService', () => {
  let service: ComplitedOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComplitedOrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
