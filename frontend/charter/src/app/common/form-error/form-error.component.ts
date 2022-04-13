import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from "@angular/forms";

@Component({
  selector: 'app-form-error',
  templateUrl: './form-error.component.html',
  styleUrls: ['./form-error.component.scss']
})
export class FormErrorComponent implements OnInit {
  @Input()
  public ctrl: AbstractControl | null = null;

  constructor() {
  }

  ngOnInit(): void {
  }

  get message() {
    const err = this.ctrl?.errors ?? {} as any;
    if ('required' in err) {
      return "This value is required";
    }
    if('min' in err){
      return `Must be greted then ${err.min.min}`
    }
    return JSON.stringify(err);
  }

}
