package de.saxsys.energymanager.resources;

import static org.assertj.core.api.Assertions.assertThat;

import de.saxsys.energymanager.DatabaseConfiguration;
import de.saxsys.energymanager.EnergyManagerApplication;
import de.saxsys.energymanager.EnergyManagerConfiguration;
import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.SolarPanel;
import de.saxsys.energymanager.db.DbUtil;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * Integration test using the production configuration.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SolarPanelsIT {

  @ClassRule
  public static final DropwizardAppRule<EnergyManagerConfiguration> APP_RULE = new DropwizardAppRule<>(
      EnergyManagerApplication.class,
      ResourceHelpers.resourceFilePath("it-config.yml")
  );

  private static SolarPanelsResourceClient CLIENT;

  private DatabaseConfiguration databaseConfiguration = APP_RULE.getConfiguration().getDatabase();

  @BeforeClass
  public static void setUp() throws Exception {
    final Client client = new JerseyClientBuilder(APP_RULE.getEnvironment())
        .build("test client");
    client.property(ClientProperties.CONNECT_TIMEOUT, 10000);
    client.property(ClientProperties.READ_TIMEOUT, 10000);
    CLIENT = new SolarPanelsResourceClient(client, APP_RULE.getLocalPort());
  }

  @Test
  public void step01ASolarPanelShouldBeCreated() throws Exception {
    assertThat(DbUtil.countCustomers(databaseConfiguration)).isZero();

    CLIENT.createSolarPanel(new SolarPanel("aPanel"), Response.class, response -> {
      assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
      assertThat(DbUtil.countCustomers(databaseConfiguration)).isEqualTo(1);
    });
  }

  @Test
  public void step02ASolarPanelShouldBeMonitored() throws Exception {
    final SolarPanel solarPanel = new SolarPanel("aPanel");

    CLIENT.retrieveMonitoringData(1, 3, MonitoringData.class, monitoringData -> {
      assertThat(monitoringData).isNotNull();
      assertThat(monitoringData.getSolarPanel()).isEqualTo(solarPanel);
    });
  }

  @Test
  public void step03ASolarPanelMonitoringThrowsNotFoundWhenSolarPanelDoesNotExist() throws Exception {
    CLIENT.retrieveMonitoringData(0, 3, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND));
  }

  @Test
  public void step04ASolarPanelMonitoringThrowsBadRequestWhenDaysIsANegativeNumber() throws Exception {
    CLIENT.retrieveMonitoringData(1, -1, Response.class, response ->
        assertThat(response.getStatusInfo()).isEqualTo(Status.BAD_REQUEST));
  }
}
