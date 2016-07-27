package de.saxsys.energymanager;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.saxsys.energymanager.configuration.EnergyManagerConfiguration;
import de.saxsys.energymanager.resources.SolarPanelsResource;

import org.junit.Before;
import org.junit.Test;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;

/**
 * Unit test for {@link EnergyManagerApplication}.
 */
public class EnergyManagerApplicationTest {

  private final Environment environment = mock(Environment.class);
  private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
  private final EnergyManagerApplication application = new EnergyManagerApplication();
  private final EnergyManagerConfiguration energyManagerConfiguration = new EnergyManagerConfiguration();

  @Before
  public void setup() throws Exception {
    when(environment.jersey()).thenReturn(jersey);
  }

  @Test
  public void shouldRegisterSolarPanelResource() throws Exception {
    application.run(energyManagerConfiguration, environment);

    verify(jersey).register(isA(SolarPanelsResource.class));
  }

}
