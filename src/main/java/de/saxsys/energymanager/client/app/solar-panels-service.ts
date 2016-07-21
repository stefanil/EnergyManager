import { Injectable } from '@angular/core';
import {EventEmitter} from '@angular/core';
import {Http, Headers} from "@angular/http";
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';

import {SolarPanel} from './solar-panel';
import {MonitoringData} from './monitoring-data';

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

  getMonitoringData(id:number, days:number) {
    return this.http.get(this.solarPanelsUrl + "/" + id + "/" + days, this.headers)
        .map(response => {
          return <MonitoringData>response.json();
        }).share()
        .catch(this.handleError);
  }

  private handleError(error:any) {
      console.error('An error occurred', error);
      return Observable.throw(error.message || error);
  }
}
