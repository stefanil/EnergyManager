import { Component } from '@angular/core';

// class definition
export class SolarPanel {
  name: string;
}
export class MonitoringData {
  solarPanel: SolarPanel;
  entries: number[];
}
export class MonitoringDataInput {
  id: number;
  days: number;
}

// mocked data
const ALL_MONITORING_DATA: MonitoringData[] = [
  {
    solarPanel: { name: 'Panel One' },
    entries: [ 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ]
  },
  {
    solarPanel: { name: 'Panel Two' },
    entries: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
  }
]

@Component({
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <h2>All Solar Panels</h2>
    <ul class="monitoringData">
      <!-- template definition with *ngFor -->
          <!-- style class is only applied if selected-->
          <!-- event binding to click event -->
      <li *ngFor="let monitoringData of allMonitoringData"
          [class.selected]="monitoringData === selectedMonitoringData"
          (click)="onSelect(monitoringData)">
        <span class="badge">{{monitoringData.solarPanel.name}}</span>
      </li>
    </ul>
    <h2>Monitoring Data</h2>
    <div>
      <label>ID: </label>
      <input [(ngModel)]="monitoringDataInput.id" placeholder="days">
    </div>
    <div>
      <label>Days: </label>
      <input [(ngModel)]="monitoringDataInput.days" placeholder="days">
    </div>
    <button>
    <div *ngIf="selectedMonitoringData">
      <h3>Monitoring Data for {{selectedMonitoringData.solarPanel.name}}</h3>
      <div>
        <p>Chart with monioring entries for {{monitoringDataInput.days}} days.</p>
      </div>
    </div>
    `,
    styles: [`
    .selected {
      background-color: #CFD8DC !important;
      color: white;
    }
    .monitoringData {
      margin: 0 0 2em 0;
      list-style-type: none;
      padding: 0;
      width: 15em;
    }
    .monitoringData li {
      cursor: pointer;
      position: relative;
      left: 0;
      background-color: #EEE;
      margin: .5em;
      padding: .3em 0;
      height: 1.6em;
      border-radius: 4px;
    }
    .monitoringData li.selected:hover {
      background-color: #BBD8DC !important;
      color: white;
    }
    .monitoringData li:hover {
      color: #607D8B;
      background-color: #DDD;
      left: .1em;
    }
    .monitoringData .text {
      position: relative;
      top: -3px;
    }
    .monitoringData .badge {
    display: inline-block;
    font-size: small;
    color: white;
    padding: 0.8em 0.7em 0 0.7em;
    background-color: #607D8B;
    line-height: 1em;
    position: relative;
    left: -1px;
    top: -4px;
    height: 1.8em;
    margin-right: .8em;
    border-radius: 4px 0 0 4px;
  }
  `]
})
export class AppComponent {
  title = 'Distributed Energy Management'
  monitoringDataInput: MonitoringDataInput = {
    id: 1,
    days: 3
  }
  allMonitoringData = ALL_MONITORING_DATA;
  selectedMonitoringData: MonitoringData;

  onSelect(monitoringData: MonitoringData) {
    this.selectedMonitoringData = monitoringData;
  };
}
