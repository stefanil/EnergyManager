/**
 *  details
 */
import { Component, Input, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass} from '@angular/common';
import {CHART_DIRECTIVES} from 'ng2-charts';

import {MonitoringData, MonitoringEntry} from './monitoring-data';
import {MonitoringDataInput} from './monitoring-data-input';
import {SolarPanelsService} from './solar-panels-service';

@Component({
  selector: 'monitoring-data',
  template: `
    <div *ngIf="solarPanelId">
      <h2>Monitoring Data</h2>
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
    inputs:['solarPanelId']
})
export class MonitoringDataComponent implements OnChanges {

  @Input() solarPanelId:number;

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

  constructor(private solarPanelsService: SolarPanelsService) {
  }

  ngOnChanges(changes: SimpleChanges) {
    var solarPanelIdChange = changes['solarPanelId'];
    if(solarPanelIdChange != null && solarPanelIdChange.currentValue != null) {
      this.loadMonitoringData(solarPanelIdChange.currentValue);
    }
  }

  loadMonitoringData(id:number) {
    this.solarPanelsService.getMonitoringData(id, 3).subscribe(
        (monitoringData:MonitoringData) => {
          this.lineChartLabels = monitoringData.entries
              .map((entry, index) => index)
              .reverse();
          this.lineChartData = [
            {
              data: monitoringData.entries
                        .map(entry => entry.generatorPower)
                        .reverse(),
              label: monitoringData.solarPanel.name
            }
          ];
        }, error => {
          console.log(error)
        }
    );
  }
}
