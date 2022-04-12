import {Component, OnInit} from '@angular/core';
import {Customer, CustomersService} from "./customers.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent implements OnInit {
  loading = false;
  customers: CustomerRow[] = [];

  constructor(private customerService: CustomersService, private navi: Router) {
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
    this.customerService.delete(customer).subscribe(removed => {
      this.customers = this.customers.filter(c => c.id != removed.id);
    });
  }

  onCreateClick() {
    this.navi.navigate(["customers", "create"]);
  }
}

interface CustomerRow extends Customer {
  busy: boolean;
}
