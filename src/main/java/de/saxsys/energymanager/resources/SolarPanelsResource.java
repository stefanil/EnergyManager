package de.saxsys.energymanager.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import de.saxsys.energymanager.model.SolarPanelDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path(value = "/solarPanels")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SolarPanelsResource {

  private final SolarPanelDao solarPanelDao;

  public SolarPanelsResource(final SolarPanelDao solarPanelDao) {
    this.solarPanelDao = solarPanelDao;
  }

  // TODO II.ii.a.1

}
