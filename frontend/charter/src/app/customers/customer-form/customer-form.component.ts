import {Component, Injectable, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Customer, CustomersService} from "../customers.service";
import {ActivatedRoute, ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {finalize, Observable} from "rxjs";

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.scss']
})
export class CustomerFormComponent implements OnInit {
  form: FormGroup;
  isLoading = false;

  constructor(private fb: FormBuilder,
              private customers: CustomersService,
              private router: Router,
              private route: ActivatedRoute
  ) {
    this.form = fb.group({
      id: null,
      name: null
    })
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data['customer']) {
        this.form.reset(data['customer']);
      }
    })
  }

  onSaveClick() {
    if (this.form.valid) {
      this.isLoading = true;
      this.customers.save(this.form.value as Customer)
        .pipe(finalize(() => this.isLoading = false))
        .subscribe(saved => this.router.navigate(["customers"]));
    }
  }
}


@Injectable()
export class CustomerResolver implements Resolve<Customer> {
  constructor(private customersService: CustomersService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Customer> | Promise<Customer> | Customer {
    const id = Number.parseInt(route.paramMap.get("id") as string, 10);
    return this.customersService.get(id);
  }
}
