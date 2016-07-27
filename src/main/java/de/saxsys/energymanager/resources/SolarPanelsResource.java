package de.saxsys.energymanager.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.model.SolarPanelDao;

import com.google.inject.persist.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path(value = "/solarPanels")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SolarPanelsResource {

  private static final Logger LOG = LoggerFactory.getLogger(SolarPanelsResource.class);

  private final SolarPanelDao solarPanelDao;

  public SolarPanelsResource(final SolarPanelDao solarPanelDao) {
    this.solarPanelDao = solarPanelDao;
  }

  @POST
  @Transactional
  public Response createSolarPanel(@Valid @NotNull final SolarPanel solarPanel) {
    solarPanelDao.addSolarPanel(solarPanel);
    return Response.ok().build();
  }

}
