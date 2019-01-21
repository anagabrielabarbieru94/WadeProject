import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationcomponentComponent } from './locationcomponent.component';

describe('LocationcomponentComponent', () => {
  let component: LocationcomponentComponent;
  let fixture: ComponentFixture<LocationcomponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationcomponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
