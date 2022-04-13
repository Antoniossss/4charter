import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PurchasesComponent} from './purchases.component';
import {PurchasesService} from "./purchases.service";
import {RouterTestingModule} from "@angular/router/testing";

describe('PurchasesComponent', () => {
  let component: PurchasesComponent;
  let fixture: ComponentFixture<PurchasesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[
        RouterTestingModule
      ],
      providers: [
        {provide: PurchasesService, useValue: {}}//dosomething
      ],
      declarations: [PurchasesComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
