import { Component } from '@angular/core';

// class definition
export class SolarPanel {
  name: string;
}
export class MonitoringEntry {
  generatorPower: number;
}
export class MonitoringData {
  //solarPanel: SolarPanel;
  //entries: Array<MonitoringEntry>;
  days: number;
}

@Component({
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <h2>All Solar Panels</h2>
    <h2>Monitoring Data for {{solarPanel.name}}</h2>
    <div>
      <label>Days: </label>
      <input [(ngModel)]="monitoringData.days" placeholder="days">
    </div>
    <div>
      <p>Chart with monioring entries for {{monitoringData.days}} days.</p>
    </div>
    `
})
export class AppComponent {
  title = 'Distributed Energy Management'
  solarPanel: SolarPanel = {
    name: 'A Panel'
  }
  monitoringData: MonitoringData = {
    days: 3
  }
}
