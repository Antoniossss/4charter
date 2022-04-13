import {Component, OnInit} from '@angular/core';
import {Customer, CustomersService} from "./customers.service";
import {Router} from "@angular/router";
import {NotifyService} from "../common/notify.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent implements OnInit {
  loading = false;
  customers: CustomerRow[] = [];

  constructor(private customerService: CustomersService,
              private navi: Router,
              private notify: NotifyService
  ) {
  }

  ngOnInit(): void {
    this.customerService.listCustomers().subscribe(customers =>
      this.customers = customers.map(c => ({
        ...c,
        busy: false,
      })));
  }

  onEditClick(customer: CustomerRow) {
    this.navi.navigate(["customers", "edit", customer.id])
  }

  onDeleteClick(customer: CustomerRow) {
    customer.busy = true;
    this.customerService.delete(customer).pipe(
      finalize(() => customer.busy = false)
    ).subscribe(removed => {
      this.customers = this.customers.filter(c => c.id != removed.id);
    }, error => this.notify.consumeHttpError(error));
  }

  onCreateClick() {
    this.navi.navigate(["customers", "create"]);
  }

  showClientPurchases(customer: CustomerRow) {
    this.navi.navigate(['purchases', 'customer', customer.id]);
  }
}

interface CustomerRow extends Customer {
  busy: boolean;
}
