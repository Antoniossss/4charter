import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PointsComponent} from './points.component';
import {PurchasesService} from "../purchases.service";
import {ReactiveFormsModule} from "@angular/forms";

describe('PointsComponent', () => {
  let component: PointsComponent;
  let fixture: ComponentFixture<PointsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule],
      providers: [
        {provide: PurchasesService, useValue: {}}//do something usefull here actually
      ],
      declarations: [PointsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
