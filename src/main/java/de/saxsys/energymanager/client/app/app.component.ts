import { Component } from '@angular/core';

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass} from '@angular/common';
import {CHART_DIRECTIVES} from 'ng2-charts';

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
      <base-chart class="chart"
          [datasets]="lineChartData"
          [labels]="lineChartLabels"
          [options]="lineChartOptions"
          [colors]="lineChartColours"
          [legend]="lineChartLegend"
          [chartType]="lineChartType">
      </base-chart>
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
  `],
  directives: [CHART_DIRECTIVES, NgClass, CORE_DIRECTIVES, FORM_DIRECTIVES],
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
    this.lineChartData = [
      {
        data: this.selectedMonitoringData.entries,//[65, 59, 80, 81, 56, 55, 40],
        label: this.selectedMonitoringData.solarPanel.name
      }
    ];
  };

  // line chart
  public lineChartData:Array<any>;
  public lineChartLabels:Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartOptions:any = {
    animation: false,
    responsive: true
  };
  public lineChartColours:Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
}
