package de.saxsys.energymanager.resources;

import static com.google.common.collect.Lists.newArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.model.SolarPanelDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

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
    client = new SolarPanelsResourceClient(
        resources.client(),
        SolarPanelsResourceClient.NO_LOCAL_PORT_FOR_RESOURCE_TESTS
    );
  }

  @Test
  public void solarPanelShouldBeCreated() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, "SolarPanel_0_0");

    client.createSolarPanel(solarPanel, Response.class, response -> {
      assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
      verify(solarPanelDao).addSolarPanel(solarPanel);
    });
  }

  @Test
  public void solarPanelNameShouldNotBeNull() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, null);

    client.createSolarPanel(solarPanel, Response.class, response ->{
      assertThat(response.getStatus()).isEqualTo(422);  // 422 .. unprocessable entity
    });
  }

  @Test
  public void solarPanelNameShouldNotBeEmpty() throws Exception {
    final SolarPanel solarPanel = new SolarPanel(null, "");

    client.createSolarPanel(solarPanel, Response.class, response ->{
      assertThat(response.getStatus()).isEqualTo(422);  // 422 .. unprocessable entity
    });
  }

  @Test
  public void aSolarPanelShouldBeMonitored() throws Exception {
    final int id = 0;
    final int days = 3;
    final SolarPanel solarPanel = new SolarPanel((long) id, "aPanel");
    when(solarPanelDao.isMonitored(id)).thenReturn(true);
    when(solarPanelDao.generateMonitoringData(id, days))
        .thenReturn(new MonitoringData(solarPanel, newArrayList()));

    client.getMonitoringData(id, days, MonitoringData.class, monitoringData -> {
      assertThat(monitoringData.getSolarPanel()).isEqualTo(solarPanel);
      assertThat(monitoringData.getEntries()).isEmpty();
      verify(solarPanelDao).isMonitored(id);
      verify(solarPanelDao).generateMonitoringData(id, days);
    });
  }

  @Test
  public void solarPanelMonitoringThrowsNotFoundWhenSolarPanelDoesNotExist() throws Exception {
    client.getMonitoringData(0, 3, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND));
  }

  @Test
  public void solarPanelMonitoringThrowsBadRequestWhenDaysIsANegativeNumber() throws Exception {
    final int id = 0;
    when(solarPanelDao.isMonitored(id)).thenReturn(true);

    client.getMonitoringData(id, -1, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.BAD_REQUEST));
  }

  @Test
  public void shouldGetSolarPanels() throws Exception {
    final List<SolarPanel> solarPanels = newArrayList(
        new SolarPanel(0L, "panel0"), new SolarPanel(1L, "panel1"), new SolarPanel(2L, "panel2")
    );
    when(solarPanelDao.findAllSolarPanels()).thenReturn(solarPanels);

    client.getSolarPanels(allSolarPanels -> {
      assertThat(allSolarPanels).isEqualTo(solarPanels);
      verify(solarPanelDao).findAllSolarPanels();
    });
  }
}
