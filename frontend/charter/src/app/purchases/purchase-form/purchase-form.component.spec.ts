import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PurchaseFormComponent} from './purchase-form.component';
import {PurchasesService} from "../purchases.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {CustomersService} from "../../customers/customers.service";
import {ReactiveFormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {NEVER} from "rxjs";

describe('PurchaseFormComponent', () => {
  let component: PurchaseFormComponent;
  let fixture: ComponentFixture<PurchaseFormComponent>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule
      ],
      providers: [
        {provide: PurchasesService, useValue: {}},//do something usefull here actually
        {provide: CustomersService, useValue: {listCustomers: () => NEVER}}//do something usefull here actually
      ],
      declarations: [PurchaseFormComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
