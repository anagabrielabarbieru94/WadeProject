import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAnAdviceComponent } from './get-an-advice.component';

describe('GetAnAdviceComponent', () => {
  let component: GetAnAdviceComponent;
  let fixture: ComponentFixture<GetAnAdviceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetAnAdviceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAnAdviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
