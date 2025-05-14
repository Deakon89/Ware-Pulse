import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplitedOrdersComponent } from './completed-orders.component';

describe('ComplitedOrdersComponent', () => {
  let component: ComplitedOrdersComponent;
  let fixture: ComponentFixture<ComplitedOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplitedOrdersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComplitedOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
