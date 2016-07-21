/**
 *  master
 */
import {Component} from '@angular/core';
import {HTTP_PROVIDERS} from '@angular/http';
import 'rxjs/Rx';

import {SolarPanel} from './solar-panel';
import {MonitoringData} from './monitoring-data';

import {SolarPanelCreationFormComponent} from './solar-panel-creation-form.component';
import {MonitoringDataComponent} from './monitoring-data.component';
import {SolarPanelsService} from './solar-panels-service';

@Component({
  selector: 'my-app',
  template: `
    <div class="container">
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

      <table class="table table-striped">
        <tr>
          <th>#</th>
          <th>Name</th>
          <th>Color</th>
          <th>Monitor</th>
        </tr>
        <tr *ngFor="let solarPanel of solarPanels">
          <td>{{solarPanel.id}}</td>
          <td>{{solarPanel.name}}</td>
          <td>TODO</td>
          <td><input type="checkbox" value=""></td>
        </tr>
      </table>

      <solar-panel-creation-form></solar-panel-creation-form>

      <monitoring-data [monitoringData]="selectedMonitoringData">
      </monitoring-data>
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
  directives: [SolarPanelCreationFormComponent, MonitoringDataComponent],
  providers: [SolarPanelsService, HTTP_PROVIDERS]
})
export class AppComponent {
  title = 'Distributed Energy Management'
  allMonitoringData:MonitoringData[];
  selectedMonitoringData:MonitoringData;
  // TODO show solarPanels + error
  solarPanels:SolarPanel[];
  error:any;

  constructor(private solarPanelsService: SolarPanelsService) {
  }

  ngOnInit() {
    this.solarPanelsService.getAllMonitoringData()
        .then(monitoringData => this.allMonitoringData = monitoringData);

    this.getSolarPanels();
    this.solarPanelsService.onSolarPanelsChange
        .subscribe(() => this.getSolarPanels());
  }

  getSolarPanels() {
    this.solarPanelsService.getSolarPanels().subscribe(
        solarPanels => {this.solarPanels = solarPanels;},
        error => {this.error = error;}
    );
  }

  onSelect(monitoringData: MonitoringData) {
    this.selectedMonitoringData = monitoringData;
  }
}
