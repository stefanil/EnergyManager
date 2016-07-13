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
    this.client = new SolarPanelsResourceClient(client, -1);
  }

  @Test
  public void solarPanelShouldBeCreated() throws Exception {
    final SolarPanel solarPanel = new SolarPanel("SolarPanel_0_0");

    client.createSolarPanel(solarPanel, Response.class, response -> {
      assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
      verify(solarPanelDao).addSolarPanel(solarPanel);
    });
  }

  @Test
  public void aSolarPanelShouldBeMonitored() throws Exception {
    final int id = 0;
    final int days = 3;
    final SolarPanel solarPanel = new SolarPanel("aPanel");
    when(solarPanelDao.isMonitored(id)).thenReturn(true);
    when(solarPanelDao.generateMonitoringData(id, days))
        .thenReturn(new MonitoringData(solarPanel, newArrayList()));

    client.retrieveMonitoringData(id, days, MonitoringData.class, monitoringData -> {
      assertThat(monitoringData).isNotNull();
      assertThat(monitoringData.getSolarPanel()).isEqualTo(solarPanel);
      assertThat(monitoringData.getEntries()).isEmpty();
      verify(solarPanelDao).isMonitored(id);
      verify(solarPanelDao).generateMonitoringData(id, days);
    });
  }

  @Test
  public void solarPanelMonitoringThrowsNotFoundWhenSolarPanelDoesNotExist() throws Exception {
    client.retrieveMonitoringData(0, 3, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND));
  }

  @Test
  public void solarPanelMonitoringThrowsBadRequestWhenDaysIsANegativeNumber() throws Exception {
    final int id = 0;
    when(solarPanelDao.isMonitored(id)).thenReturn(true);

    client.retrieveMonitoringData(id, -1, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.BAD_REQUEST));
  }

}
