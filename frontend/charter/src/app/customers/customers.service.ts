import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CustomersService {
  constructor(private http: HttpClient) {
  }

  public listCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>("/customer/list")
  }

  save(customer: Customer): Observable<Customer> {
    if (customer.id) {
      return this.http.put<Customer>(`customer/${customer.id}`, customer);
    }
    return this.http.post<Customer>(`customer`, customer)
  }

  get(id: number): Observable<Customer> {
    return this.http.get<Customer>(`customer/${id}`);
  }

  delete(customer: Customer): Observable<Customer> {
    return this.http.delete<Customer>(`customer/${customer.id}`);
  }
}


export interface Customer {
  id: number,
  name: string
}
