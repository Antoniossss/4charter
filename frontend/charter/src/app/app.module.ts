import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CustomersComponent} from './customers/customers.component';
import {PurchasesComponent} from './purchases/purchases.component';
import {ENVIRONMENT} from "./types";
import {environment} from "../environments/environment";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CustomerFormComponent, CustomerResolver} from './customers/customer-form/customer-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import {RestHostInterceptor} from "./rest-host-interceptor";
import {PurchaseFormComponent, PurchaseResolver} from './purchases/purchase-form/purchase-form.component';
import {FormErrorComponent} from './common/form-error/form-error.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomersComponent,
    PurchasesComponent,
    CustomerFormComponent,
    PurchaseFormComponent,
    FormErrorComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    {provide: ENVIRONMENT, useValue: environment},
    {provide: HTTP_INTERCEPTORS, useClass: RestHostInterceptor, multi: true},
    CustomerResolver, PurchaseResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
