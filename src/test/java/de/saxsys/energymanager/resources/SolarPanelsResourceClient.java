 package de.saxsys.energymanager.resources;

import de.saxsys.energymanager.api.SolarPanel;

import org.glassfish.jersey.client.JerseyClient;

import java.util.function.Consumer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Test wrapper for accessing {@link SolarPanelsResource}.
 */
public class SolarPanelsResourceClient extends JerseyClient {

  private final static String BASE_PATH = "/solarPanels";

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
        ? String.format("http://localhost:%d" + BASE_PATH, localPort)
        : BASE_PATH;
  }

  public <T> void createSolarPanel(final SolarPanel solarPanel, final Class<T> responseType,
      final Consumer<T> postCondition) {
    postCondition.accept(
        client.target(baseUri)
            .request()
            .post(Entity.entity(solarPanel, MediaType.APPLICATION_JSON), responseType)
    );
  }

  public <T> void retrieveMonitoringData(final int solarPanelId, final int days, final Class<T> responseType,
      final Consumer<T> postCondition) {
    postCondition.accept(
        client.target(baseUri + "/" + solarPanelId + "/" + days)
            .request()
            .get(responseType)
    );
  }
}
