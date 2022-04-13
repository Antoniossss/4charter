import {Component, Injectable, OnInit} from '@angular/core';
import {Purchase, PurchasesService} from "../purchases.service";
import {Customer, CustomersService} from "../../customers/customers.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {combineLatest, filter, finalize, Observable, ReplaySubject} from "rxjs";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-purchase-form',
  templateUrl: './purchase-form.component.html',
  styleUrls: ['./purchase-form.component.scss'],
  providers: [DatePipe],
})
export class PurchaseFormComponent implements OnInit {
  isLoading = false;
  allCustomers = new ReplaySubject<Customer[]>();
  form: FormGroup;

  constructor(private purcheses: PurchasesService,
              private customers: CustomersService,
              fb: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private datePipe: DatePipe,
  ) {
    this.form = fb.group({
      id: null,
      customer: [null, Validators.required],
      purchaseDate: [null, Validators.required],
      price: [null, [Validators.required, Validators.min(0)]]
    })
  }

  ngOnInit(): void {
    this.customers.listCustomers().subscribe(v => this.allCustomers.next(v));
    combineLatest(this.route.data, this.allCustomers)
      .pipe(filter(v => 'purchase' in v[0]))
      .subscribe(([data, customers]) => {
        const purchase = data['purchase'] as Purchase;
        const formattedDate = this.datePipe.transform(purchase.purchaseDate, "yyyy-MM-dd");
        console.log("formatted??", formattedDate, purchase.purchaseDate.toISOString());
        if (purchase) {
          const toReset = {
            id: purchase.id,
            customer: customers.find(c => c.id === purchase.customer.id),
            purchaseDate: formattedDate,
            price: purchase.price
          };
          this.form.reset(toReset);
        }
      })
  }

  onSaveClick() {
    if (this.form.invalid) {
      return;
    }
    const v = this.form.value;
    const p: Purchase = {
      ...v,
      purchaseDate: new Date(v.purchaseDate)
    }
    this.isLoading = true;
    this.purcheses.save(p).pipe(finalize(() => this.isLoading = false)).subscribe(saved => {
      this.router.navigate(["purchases"]);
    })
  }
}

@Injectable()
export class PurchaseResolver implements Resolve<Purchase> {
  constructor(private purchases: PurchasesService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Purchase> | Promise<Purchase> | Purchase {
    const id = Number.parseInt(route.paramMap.get("id") as string, 10);
    console.log("getting purhcase??", id);
    return this.purchases.get(id);
  }
}
