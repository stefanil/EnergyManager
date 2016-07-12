package de.saxsys.energymanager;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.saxsys.energymanager.db.DbShutdownTask;
import de.saxsys.energymanager.health.DatabaseHealthCheck;
import de.saxsys.energymanager.resources.SolarPanelsResource;

import com.codahale.metrics.health.HealthCheckRegistry;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jersey.validation.DropwizardConfiguredValidator;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Environment;

/**
 * Unit test for {@link EnergyManagerApplication}.
 */
public class EnergyManagerApplicationTest {

  private final Environment environment = mock(Environment.class);
  private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
  private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
  private final DropwizardConfiguredValidator validator = mock(DropwizardConfiguredValidator.class);
  private final AdminEnvironment adminEnvironment = mock(AdminEnvironment.class);
  private final EnergyManagerApplication application = new EnergyManagerApplication();
  private final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
  private final EnergyManagerConfiguration energyManagerConfiguration = new EnergyManagerConfiguration();
  private final ServletEnvironment servletEnvironment = mock(ServletEnvironment.class);
  private final Dynamic filterRegistration = mock(Dynamic.class);

  @Before
  public void setup() throws Exception {
    energyManagerConfiguration.setDatabase(databaseConfiguration);
    when(environment.jersey()).thenReturn(jersey);
    when(environment.healthChecks()).thenReturn(healthChecks);
    when(environment.getValidator()).thenReturn(validator);
    when(environment.admin()).thenReturn(adminEnvironment);
    doNothing().when(adminEnvironment).addTask(isA(Task.class));
    when(environment.servlets()).thenReturn(servletEnvironment);
    when(servletEnvironment.addFilter(eq("persist-filter"), isA(Filter.class)))
        .thenReturn(filterRegistration);
  }

  @Test
  public void shouldRegisterSolarPanelResource() throws Exception {
    application.run(energyManagerConfiguration, environment);

    verify(jersey).register(isA(SolarPanelsResource.class));
  }

  @Test
  public void shouldRegisterDatabaseHealthCheck() throws Exception {
    application.run(energyManagerConfiguration, environment);

    verify(healthChecks).register(eq("database"), eq(new DatabaseHealthCheck(databaseConfiguration)));
  }

  @Test
  public void shouldAddDbShutdownTask() throws Exception {
    application.run(energyManagerConfiguration, environment);

    verify(adminEnvironment).addTask(isA(DbShutdownTask.class));
  }
}
