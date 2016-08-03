package de.saxsys.energymanager.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.model.SolarPanelDao;

import com.google.inject.persist.Transactional;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path(value = "/solarPanels")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SolarPanelsResource {

  // TODO II_iii instantiate slf4j logger

  private final SolarPanelDao solarPanelDao;

  public SolarPanelsResource(final SolarPanelDao solarPanelDao) {
    this.solarPanelDao = solarPanelDao;
  }

  @POST
  @Transactional
  public Response createSolarPanel(@Valid @NotNull final SolarPanel solarPanel) {
    // TODO II_iii write a debug log message

    solarPanelDao.addSolarPanel(solarPanel);
    return Response.ok().build();
  }

  @GET
  @Path("{id}/{days}")
  @Transactional
  public MonitoringData getMonitoringData(@PathParam("id") int id, @PathParam("days") int days) {
    // TODO II_iii write a info log message

    if (!solarPanelDao.isMonitored(id)) {
      final String message = "Solar panel with id " + id + " was not found.";
      // TODO II_iii write a warn log message
      throw new WebApplicationException(message, Status.NOT_FOUND);
    }
    if (days < 0) {
      final String message = "Days must be a positive integer value.";
      // TODO II_iii write a error log message
      throw new WebApplicationException(message, Status.BAD_REQUEST);
    }

    return solarPanelDao.generateMonitoringData(id, days);
  }

  @GET
  public List<SolarPanel> getSolarPanels() {
    return solarPanelDao.findAllSolarPanels();
  }
}
