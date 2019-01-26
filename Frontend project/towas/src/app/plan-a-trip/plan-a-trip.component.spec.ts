import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanATripComponent } from './plan-a-trip.component';

describe('PlanATripComponent', () => {
  let component: PlanATripComponent;
  let fixture: ComponentFixture<PlanATripComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanATripComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanATripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
