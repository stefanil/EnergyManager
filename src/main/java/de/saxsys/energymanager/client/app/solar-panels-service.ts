import { Injectable } from '@angular/core';

import {ALL_MONITORING_DATA} from './monitoring-data';

@Injectable()
export class SolarPanelsService {

  getAllMonitoringData() {
    return Promise.resolve(ALL_MONITORING_DATA);
  }
}
