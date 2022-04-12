import {environment} from "../environments/environment";
import {InjectionToken} from "@angular/core";

export const ENVIRONMENT = new InjectionToken<Environment>("Environment");
export type Environment = typeof environment;
