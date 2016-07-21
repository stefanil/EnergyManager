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
  templateUrl: 'app/app.component.html',
  styleUrls: ['app/app.component.css'],
  directives: [SolarPanelCreationFormComponent, MonitoringDataComponent],
  providers: [SolarPanelsService, HTTP_PROVIDERS]
})
export class AppComponent {
  title = 'Distributed Energy Management'
  selectedMonitoringData:MonitoringData;
  selectedSolarPanel:SolarPanel;
  solarPanels:SolarPanel[];
  error:any;

  constructor(private solarPanelsService: SolarPanelsService) {
  }

  ngOnInit() {
    this.getSolarPanels();
    this.solarPanelsService.onSolarPanelsChange
        .subscribe(() => this.getSolarPanels());
  }

  getSolarPanels() {
    this.solarPanelsService.getSolarPanels().subscribe(
        solarPanels => {this.solarPanels = solarPanels;},
        error => {console.log(error)}
    );
  }

  showMonitoringData(solarPanel:SolarPanel) {
    this.selectedSolarPanel = solarPanel;
  }
}
