import {SolarPanel} from './solar-panel';

export class MonitoringData {
  solarPanel:SolarPanel;
  entries:MonitoringEntry[];
}

export class MonitoringEntry {
  generatorPower:number;
}
