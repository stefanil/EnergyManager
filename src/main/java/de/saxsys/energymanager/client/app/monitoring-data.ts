import {SolarPanel} from './solar-panel';

export class MonitoringData {
  solarPanel: SolarPanel;
  entries: number[];
}

export const ALL_MONITORING_DATA: MonitoringData[] = [
  {
    solarPanel: { name: 'Panel One' },
    entries: [ 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ]
  },
  {
    solarPanel: { name: 'Panel Two' },
    entries: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
  }
]
