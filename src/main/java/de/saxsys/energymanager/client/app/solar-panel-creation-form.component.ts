import {Component} from '@angular/core';
import {NgForm}    from '@angular/forms';
import { AlertComponent } from 'ng2-bootstrap/components/alert';

import {SolarPanel}    from './solar-panel';
import {SolarPanelsService} from './solar-panels-service';

@Component({
  selector: 'solar-panel-creation-form',
  templateUrl: 'app/solar-panel-creation-form.component.html',
  providers: [SolarPanelsService],
  directives: [AlertComponent]
})
export class SolarPanelCreationFormComponent {
  model:SolarPanel;
  active = true;
  alerts:Array<Object> = [ ];

  constructor(private solarPanelsService: SolarPanelsService) {
  }

  ngOnInit() {
    this.newSolarPanel();
  }

  onSubmit() {
    this.solarPanelsService.createSolarPanel(this.model).subscribe(
      repsonse => {
        this.addAlert("Solar Panel creation successful.", "success", 3000);
        this.solarPanelsService.getSolarPanels();
      },
      error => {
        this.addAlert(`Fehler.`, "error");
      }
    );

  }

  newSolarPanel() {
    this.model = new SolarPanel(null, '');
    this.active = false;
    setTimeout(() => this.active = true, 0);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  closeAlert(i:number) {
    this.alerts.splice(i, 1);
  }

  addAlert(message: string, style: string, dismissOnTimeout? : number) {
    this.alerts.push({msg: message, type: style, closable: true, dismissOnTimeout : dismissOnTimeout});
    console.log(this.alerts);
  }
}
