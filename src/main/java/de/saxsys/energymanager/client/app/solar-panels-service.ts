import { Injectable } from '@angular/core';
import {EventEmitter} from '@angular/core';
import {Http, Headers} from "@angular/http";
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';

import {SolarPanel} from './solar-panel';
import {ALL_MONITORING_DATA} from './monitoring-data';

@Injectable()
export class SolarPanelsService {

  onSolarPanelsChange:EventEmitter<boolean> = new EventEmitter<boolean>();

  private headers = {
    headers : new Headers({
      'Content-Type': 'application/json'
    })
  };
  private baseUrl = 'http://localhost:8080';
  private solarPanelsUrl = this.baseUrl + '/solarPanels';

  constructor(private http:Http) {
  }

  getAllMonitoringData() {
    return Promise.resolve(ALL_MONITORING_DATA);
  }

  createSolarPanel(solarPanel: SolarPanel) {
    return this.http.post(this.solarPanelsUrl, solarPanel, this.headers)
        .share()
        .catch(this.handleError);
  }

  getSolarPanels() {
    return this.http.get(this.solarPanelsUrl, this.headers)
        .map(response => <SolarPanel[]>response.json())
        .share()
        .catch(this.handleError);
  }

  private handleError(error:any) {
      console.error('An error occurred', error);
      return Observable.throw(error.message || error);
  }
}
