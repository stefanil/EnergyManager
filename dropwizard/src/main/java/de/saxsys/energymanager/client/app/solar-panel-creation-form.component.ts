import {Component, OnInit} from '@angular/core';
import {NgForm}    from '@angular/forms';
import {AlertComponent} from 'ng2-bootstrap/components/alert';

import {SolarPanel}    from './solar-panel';
import {SolarPanelsService} from './solar-panels-service';

@Component({
  selector: 'solar-panel-creation-form',
  templateUrl: 'app/solar-panel-creation-form.component.html',
  directives: [AlertComponent]
})
export class SolarPanelCreationFormComponent implements OnInit {
  model:SolarPanel;
  active = true;
  alerts:Array<Object> = [];

  constructor(private solarPanelsService: SolarPanelsService) {
  }

  ngOnInit() {
    this.newSolarPanel();
  }

  onSubmit() {
    this.solarPanelsService.createSolarPanel(this.model).subscribe(
      repsonse => {
        this.addAlert("Solar Panel creation successful.", "success", 3000);
        this.solarPanelsService.onSolarPanelsChange.emit(true);
        this.newSolarPanel();
      },
      error => {
        this.addAlert(`An error occurred while creation.`, "error");
      }
    );

  }

  newSolarPanel() {
    this.model = new SolarPanel(null, '');
    this.active = false;
    setTimeout(() => this.active = true, 0);
  }

  closeAlert(i:number) {
    this.alerts.splice(i, 1);
  }

  addAlert(message: string, style: string, dismissOnTimeout? : number) {
    this.alerts.push({msg: message, type: style, closable: true, dismissOnTimeout : dismissOnTimeout});
    console.log(this.alerts);
  }
}
