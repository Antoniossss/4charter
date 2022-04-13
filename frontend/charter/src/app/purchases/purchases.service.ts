import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Customer} from "../customers/customers.service";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PurchasesService {

  constructor(private http: HttpClient) {
  }

  listPurchases(): Observable<Purchase[]> {
    return this.http.get<Purchase[]>("purchase").pipe(
      map(arr => arr.map(purchaseFromDto))
    );
  }

  save(p: Purchase): Observable<Purchase> {
    if (p.id) {
      return this.http.put("purchase", {
        id: p.id,
        price: p.price,
        purchaseDate: p.purchaseDate.toISOString(),
        customer: p.customer
      }).pipe(map(purchaseFromDto));
    }
    const body: NewPurchaseDto = {
      customerId: p.customer.id,
      price: p.price,
      purchaseDate: p.purchaseDate.toISOString()
    }
    return this.http.post<Purchase>("purchase", body).pipe(map(purchaseFromDto));
  }

  get(id: number): Observable<Purchase> {
    return this.http.get<Purchase>(`purchase/${id}`).pipe(map(purchaseFromDto));
  }

  delete(purchase: Purchase) {
    return this.http.delete<Purchase>(`purchase/${purchase.id}`).pipe(map(purchaseFromDto));
  }
}

export function purchaseFromDto(dto: any) {
  return {
    ...dto,
    purchaseDate: new Date(Date.parse(dto.purchaseDate))
  }
}


export interface Purchase {
  customer: Customer;
  purchaseDate: Date;
  price: number;
  id: number;
}


interface NewPurchaseDto {
  customerId: number;
  purchaseDate: string;
  price: number;
}
