/**
 *  details
 */
import { Component, Input, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';
import {NgForm}    from '@angular/forms';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass} from '@angular/common';

import {CHART_DIRECTIVES} from 'ng2-charts';

import {SolarPanel} from './solar-panel';
import {MonitoringData, MonitoringEntry} from './monitoring-data';
import {SolarPanelsService} from './solar-panels-service';

@Component({
  selector: 'monitoring-data',
  template: `
    <div *ngIf="solarPanel">
      <h2>Monitoring Data</h2>
      <!-- <div class="row"> -->
          <form class="form-inline" #daysForm="ngForm">
            <div class="form-group">
              <input placeholder="days" class="form-control" name="days" required
                  [(ngModel)]="days" (ngModelChange)="showMonitoringData(solarPanel)"
                  #fdays="ngModel">
            </div>
            <div [hidden]="fdays.valid" class="alert alert-danger">
              Amount of days is required
            </div>
          </form>

      <!-- </div> -->
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
    inputs:['solarPanel']
})
export class MonitoringDataComponent implements OnChanges {

  @Input() solarPanel:SolarPanel;

  days:number = 3;
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
    var solarPanelChange = changes['solarPanel'];
    if(solarPanelChange != null && solarPanelChange.currentValue != null) {
      this.showMonitoringData(solarPanelChange.currentValue);
    }
  }

  showMonitoringData(solarPanel:SolarPanel) {
    if(this.days) {
      this.solarPanelsService.getMonitoringData(solarPanel.id, this.days).subscribe(
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
}
