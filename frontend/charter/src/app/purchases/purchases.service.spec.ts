import {TestBed} from '@angular/core/testing';

import {PurchasesService} from './purchases.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('PurchasesService', () => {
  let service: PurchasesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(PurchasesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
