import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Inject, Injectable} from "@angular/core";
import {Environment, ENVIRONMENT} from "./types";
import {Observable} from "rxjs";


@Injectable()
export class RestHostInterceptor implements HttpInterceptor {
  constructor(@Inject(ENVIRONMENT) private env: Environment) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url = req.url;
    if (!url.startsWith("/") && !this.env.endpoint.endsWith("/")) {
      url = "/" + url;
    }
    url = this.env.endpoint + url;
    const cloned = req.clone({url})
    console.log(req.url, cloned.url);
    return next.handle(cloned);
  }

}
