import {Component, OnInit} from '@angular/core';
import {Purchase, PurchasesService} from "./purchases.service";
import {finalize} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-purchases',
  templateUrl: './purchases.component.html',
  styleUrls: ['./purchases.component.scss'],
})
export class PurchasesComponent implements OnInit {
  loading = true;
  purchases: PurchaseRow[] = [];

  constructor(private purchaseService: PurchasesService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.purchaseService.listPurchases()
      .pipe(finalize(() => this.loading = false))
      .subscribe(purchases => this.purchases = purchases.map(v => ({
        ...v,
        busy: false
      })));
  }

  onCreateClick() {
    this.router.navigate(["purchases", "create"]);
  }

  onEditClick(purchase: Purchase) {
    this.router.navigate(["purchases", "edit", purchase.id]);
  }

  onDeleteClick(purchase: PurchaseRow) {
    purchase.busy = true;
    this.purchaseService.delete(purchase)
      .pipe(finalize(() => purchase.busy = false))
      .subscribe(deleted => this.purchases = this.purchases.filter(p => p.id != deleted.id));
  }
}


interface PurchaseRow extends Purchase {
  busy: boolean;
}
