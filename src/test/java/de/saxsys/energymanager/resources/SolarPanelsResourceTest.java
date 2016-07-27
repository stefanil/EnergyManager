package de.saxsys.energymanager.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.model.SolarPanelDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * Resource test for {@link SolarPanelsResource}.
 */
public class SolarPanelsResourceTest {

  private final SolarPanelDao solarPanelDao = mock(SolarPanelDao.class);
  private final SolarPanelsResource cut = new SolarPanelsResource(solarPanelDao);

  @Rule
  public final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(cut)
      .build();

  private SolarPanelsResourceClient client;

  @Before
  public void setUp() throws Exception {
    final Client client = resources.client();
    this.client = new SolarPanelsResourceClient(client, SolarPanelsResourceClient.NO_LOCAL_PORT_FOR_RESOURCE_TESTS);
  }

  // II.ii.a.1
  @Test
  public void solarPanelShouldBeCreated() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, "SolarPanel_0_0");

    client.createSolarPanel(solarPanel, Response.class, response -> {
      assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
      verify(solarPanelDao).addSolarPanel(solarPanel);
    });
  }

  // II.ii.a.2
  @Test
  public void solarPanelNameShouldNotBeNull() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, null);

    client.createSolarPanel(solarPanel, Response.class, response ->{
      assertThat(response.getStatus()).isEqualTo(422);  // 422 .. unprocessable entity
    });
  }

  // II.ii.a.2
  @Test
  public void solarPanelNameShouldNotBeEmpty() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, "");

    client.createSolarPanel(solarPanel, Response.class, response ->{
      assertThat(response.getStatus()).isEqualTo(422);  // 422 .. unprocessable entity
    });
  }

}
