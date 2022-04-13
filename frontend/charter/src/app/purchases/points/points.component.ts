import {Component, EventEmitter, Input, OnChanges, OnDestroy, Output, SimpleChanges} from '@angular/core';
import {CustomerPoints, PointsFilter, PurchasesService} from "../purchases.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {filter, Subject, takeUntil} from "rxjs";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-points',
  templateUrl: './points.component.html',
  styleUrls: ['./points.component.scss'],
  providers: [DatePipe],
})
export class PointsComponent implements OnChanges, OnDestroy {

  @Input()
  public customerId?: number;

  private onDestroy = new Subject();

  @Output()
  public filtersChange = new EventEmitter<PointsFilter>();

  isLoading = false;
  form: FormGroup;
  results?: CustomerPoints;

  constructor(private purchaseService: PurchasesService, fb: FormBuilder, private datePipe: DatePipe) {
    this.form = fb.group({
      from: null,
      to: null
    });
    this.form.valueChanges.pipe(
      takeUntil(this.onDestroy),
      filter(() => this.form.valid),
    ).subscribe(() => this.fetchCustomerPoints());
  }

  ngOnDestroy(): void {
    this.onDestroy.next(void 1);
    this.onDestroy.complete();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['customerId']) {
      this.fetchCustomerPoints();
    }
  }

  private fetchCustomerPoints() {
    this.isLoading = true;
    const v = this.form.value;
    const filters = {
      customerId: this.customerId!,
      from: v.from ? new Date(Date.parse(v.from)) : null,
      to: v.to ? new Date(Date.parse(v.to)) : new Date(Date.now()),
    };
    this.form.patchValue({
      to: this.datePipe.transform(filters.to, "yyyy-MM-dd")
    }, {
      emitEvent: false,
      onlySelf: true
    })
    this.purchaseService.fetchPoints(filters)
      .subscribe(results => this.results = results)
    this.filtersChange.next(filters);
  }
}
