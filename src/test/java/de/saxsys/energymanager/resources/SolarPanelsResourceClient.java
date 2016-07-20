package de.saxsys.energymanager.resources;

import de.saxsys.energymanager.api.SolarPanel;

import org.glassfish.jersey.client.JerseyClient;

import java.util.List;
import java.util.function.Consumer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Test wrapper for accessing {@link SolarPanelsResource}.
 */
public class SolarPanelsResourceClient extends JerseyClient {

  public final static int NO_LOCAL_PORT_FOR_RESOURCE_TESTS = -1;

  private final static String BASE_PATH_SOLAR_PANELS_RESOURCE = "/solarPanels";

  private final Client client;
  private final String baseUri;

  /**
   * @param client
   * @param localPort local port greater than or equal to 0 is used for integration testing; choose -1 as local port
   * in context of resource unit tests
   */
  public SolarPanelsResourceClient(final Client client, final int localPort) {
    this.client = client;
    baseUri = localPort >= 0
        ? String.format("http://localhost:%d" + BASE_PATH_SOLAR_PANELS_RESOURCE, localPort)
        : BASE_PATH_SOLAR_PANELS_RESOURCE;
  }

  public <T> void createSolarPanel(final SolarPanel solarPanel, final Class<T> responseType,
      final Consumer<T> postCondition) {
    postCondition.accept(
        client.target(baseUri)
            .request()
            .post(Entity.entity(solarPanel, MediaType.APPLICATION_JSON), responseType)
    );
  }

  public <T> void getMonitoringData(final int solarPanelId, final int days, final Class<T> responseType,
      final Consumer<T> postCondition) {
    postCondition.accept(
        client.target(baseUri + "/" + solarPanelId + "/" + days)
            .request()
            .get(responseType)
    );
  }

  public void getSolarPanels(final Consumer<List<SolarPanel>> postCondition) {
    postCondition.accept(
        client.target(baseUri)
            .request()
            .get(new GenericType<List<SolarPanel>>(){
            })
    );
  }
}
