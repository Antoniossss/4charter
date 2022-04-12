import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {PurchasesComponent} from "./purchases/purchases.component";
import {CustomerFormComponent, CustomerResolver} from "./customers/customer-form/customer-form.component";

const routes: Routes = [
  {
    path: "customers",
    children: [
      {
        path: '',
        component: CustomersComponent,
      },
      {
        path: "create",
        component: CustomerFormComponent,
      },
      {
        path: "edit/:id",
        component: CustomerFormComponent,
        resolve: {
          customer: CustomerResolver
        }
      }
    ]
  },
  {
    path: "purchases",
    children: [
      {
        path: '',
        component: PurchasesComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
