package de.saxsys.energymanager;

import de.saxsys.energymanager.configuration.EnergyManagerConfiguration;
import de.saxsys.energymanager.model.SolarPanelDao;
import de.saxsys.energymanager.resources.SolarPanelsResource;

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
    environment.jersey().register(new SolarPanelsResource(SolarPanelDao.getInstance()));
    enableCrossOriginRequests(environment);
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
