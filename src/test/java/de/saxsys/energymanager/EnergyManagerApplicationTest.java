package de.saxsys.energymanager;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.saxsys.energymanager.configuration.EnergyManagerConfiguration;
import de.saxsys.energymanager.resources.SolarPanelsResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import org.assertj.core.api.Assertions;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.FilterRegistration.Dynamic;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Environment;

/**
 * Unit test for {@link EnergyManagerApplication}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Guice.class)
public class EnergyManagerApplicationTest {

  private final EnergyManagerApplication cut = new EnergyManagerApplication();

  private final Environment environment = mock(Environment.class);
  private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
  private final EnergyManagerConfiguration energyManagerConfiguration = new EnergyManagerConfiguration();
  private final ServletEnvironment servletEnvironment = mock(ServletEnvironment.class);
  private final Dynamic crossOriginFilter = mock(Dynamic.class);

  @Before
  public void setup() throws Exception {
    when(environment.jersey()).thenReturn(jersey);
    when(environment.servlets()).thenReturn(servletEnvironment);
    when(servletEnvironment.addFilter(eq("CORS"), eq(CrossOriginFilter.class)))
        .thenReturn(crossOriginFilter);
  }

  @Test
  public void shouldRegisterSolarPanelResource() throws Exception {
    PowerMockito.mockStatic(Guice.class);
    final Injector injector = mock(Injector.class);
    PowerMockito.when(Guice.createInjector(isA(Module.class))).thenAnswer(invocation -> {
      invocation.getArgumentAt(0, AbstractModule.class);
      return injector;
    });
    when(injector.getInstance(any(Class.class))).thenAnswer(invocation -> {
      final Class aClass = invocation.getArgumentAt(0, Class.class);
      Assertions.assertThat(aClass.getName()).isEqualTo("de.saxsys.energymanager.resources.SolarPanelsResource");
      return mock(SolarPanelsResource.class);
    });

    cut.run(energyManagerConfiguration, environment);

    verify(jersey).register(isA(SolarPanelsResource.class));
    verify(injector).getInstance(any(Class.class));
  }

}
