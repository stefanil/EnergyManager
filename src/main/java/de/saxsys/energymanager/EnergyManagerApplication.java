package de.saxsys.energymanager;

import de.saxsys.energymanager.configuration.EnergyManagerConfiguration;
import de.saxsys.energymanager.resources.SolarPanelsResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class EnergyManagerApplication extends Application<EnergyManagerConfiguration> {

  public static void main(final String[] args) throws Exception {
    new EnergyManagerApplication().run(args);
  }

  @Override
  public String getName() {
    return "EnergyManager";
  }

  @Override
  public void initialize(final Bootstrap<EnergyManagerConfiguration> bootstrap) {
    // nothing to do here
  }

  @Override
  public void run(final EnergyManagerConfiguration configuration, final Environment environment) {
    final Injector injector = createInjector(configuration, environment);
    // TODO v_ii register PersistFilter (use of session-per-http-request strategy)

    environment.jersey().register(injector.getInstance(SolarPanelsResource.class));

    enableCrossOriginRequests(environment);
  }

  private Injector createInjector(final EnergyManagerConfiguration configuration, final Environment environment) {
    return Guice.createInjector(
        createGuiceModule(configuration, environment)
        // TODO v_ii create and register JPAPersistModule with Guice
    );
  }

  protected Module createGuiceModule(final EnergyManagerConfiguration configuration, final Environment environment) {
    return new AbstractModule() {
      @Override
      protected void configure() {
        // nothing to do here
      }
    };
  }

  // enable angularJS 2 clients to access resources offered by this server
  private void enableCrossOriginRequests(final Environment environment) {
    final Dynamic corsFilter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,"GET,PUT,POST,DELETE,OPTIONS");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
        "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
  }

}
