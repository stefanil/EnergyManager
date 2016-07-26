package de.saxsys.energymanager.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.model.SolarPanelDao;

import com.google.inject.persist.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
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

  private static final Logger LOG = LoggerFactory.getLogger(SolarPanelsResource.class);

  private final SolarPanelDao solarPanelDao;

  @Inject
  public SolarPanelsResource(final SolarPanelDao solarPanelDao) {
    this.solarPanelDao = solarPanelDao;
  }

  @POST
  @Transactional
  public Response createSolarPanel(@Valid @NotNull final SolarPanel solarPanel) {
    LOG.debug("Creating the solar panel {}", solarPanel);

    solarPanelDao.addSolarPanel(solarPanel);
    return Response.ok().build();
  }

  @GET
  @Path("{id}/{days}")
  @Transactional
  public MonitoringData getMonitoringData(@PathParam("id") int id, @PathParam("days") int days) {
    LOG.info("Getting monitoring data for solar panel with index {}.", id);

    if (!solarPanelDao.isMonitored(id)) {
      final String message = "Solar panel with id " + id + " was not found.";
      LOG.warn(message);
      throw new WebApplicationException(message, Status.NOT_FOUND);
    }
    if (days < 0) {
      final String message = "Days must be a positive integer value.";
      LOG.error(message);
      throw new WebApplicationException(message, Status.BAD_REQUEST);
    }

    return solarPanelDao.generateMonitoringData(id, days);
  }

  @GET
  public List<SolarPanel> getSolarPanels() {
    return solarPanelDao.findAllSolarPanels();
  }
}
