import {AfterViewInit, Component, OnInit} from '@angular/core';
import {PointsFilter, Purchase, PurchasesService} from "./purchases.service";
import {finalize, map, switchMap} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-purchases',
  templateUrl: './purchases.component.html',
  styleUrls: ['./purchases.component.scss'],
})
export class PurchasesComponent implements OnInit, AfterViewInit {
  loading = true;
  purchases: PurchaseRow[] = [];
  selectedCustomerId?: number;
  activeFilters?: PointsFilter;

  constructor(private purchaseService: PurchasesService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }

  ngAfterViewInit(): void {
    this.route.params.pipe(
      map(params => params['customerId']),
      switchMap(customerId => {
        this.selectedCustomerId = customerId;
        this.loading = true;
        return this.purchaseService.listPurchases(customerId).pipe(finalize(() => this.loading = false));
      }))
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

  get showPoints() {
    return !!this.selectedCustomerId;
  }

  dateInRange(purchaseDate: Date) {
    if (this.activeFilters) {
      return (purchaseDate >= (this.activeFilters.from ?? Number.MIN_VALUE)) &&
        (purchaseDate <= (this.activeFilters.to ?? Number.MAX_VALUE))
    }
    return this.showPoints;
  }
}


interface PurchaseRow extends Purchase {
  busy: boolean;
}
