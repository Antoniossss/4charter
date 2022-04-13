import {Injectable} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NotifyService {

  constructor() {
  }

  public consumeHttpError(error: HttpErrorResponse) {
    alert(error.error?.error ?? error.message);
  }
}
