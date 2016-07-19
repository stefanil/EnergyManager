/**
 *  details
 */
import { Component, Input, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass} from '@angular/common';
import {CHART_DIRECTIVES} from 'ng2-charts';

import {MonitoringData, ALL_MONITORING_DATA} from './monitoring-data';
import {MonitoringDataInput} from './monitoring-data-input';

@Component({
  selector: 'monitoring-data',
  template: `
    <div *ngIf="monitoringData">
      <h2>Monitoring Data</h2>
      <div>
        <label>ID: </label>
        <input [(ngModel)]="monitoringDataInput.id" placeholder="days">
      </div>
      <div>
        <label>Days: </label>
        <input [(ngModel)]="monitoringDataInput.days" placeholder="days">
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
    directives: [CHART_DIRECTIVES, NgClass, CORE_DIRECTIVES, FORM_DIRECTIVES],
    inputs:['monitoringData']
})
export class MonitoringDataComponent
implements OnChanges
{
  @Input()  monitoringData:MonitoringData;
  monitoringDataInput: MonitoringDataInput = {
    id: 1,
    days: 3
  }
  lineChartData:Array<any>;
  lineChartLabels:Array<any>;
  lineChartOptions:any = {
    animation: false,
    responsive: true
  };
  lineChartColours:Array<any> = [
    {
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  lineChartLegend:boolean = true;
  lineChartType:string = 'line';

  ngOnChanges(changes: SimpleChanges) {
    var monitoringDataChange:SimpleChange = changes['monitoringData'];
    if(monitoringDataChange != null && monitoringDataChange.currentValue) {
      this.lineChartLabels = this.monitoringData.entries.map((entry, index) => index);
      this.lineChartData = [
         {
           data: this.monitoringData.entries,
           label: this.monitoringData.solarPanel.name
         }
      ];
    }
  }
}
