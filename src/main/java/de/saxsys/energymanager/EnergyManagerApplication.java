package de.saxsys.energymanager;

import static de.saxsys.energymanager.db.DbUtil.createJpaPersistModule;

import de.saxsys.energymanager.db.SolarPanelDao;
import de.saxsys.energymanager.db.DbShutdownTask;
import de.saxsys.energymanager.health.DatabaseHealthCheck;
import de.saxsys.energymanager.resources.SolarPanelsResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javax.validation.Validator;

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

    environment.jersey().register(new SolarPanelsResource(injector.getInstance(SolarPanelDao.class)));
    environment.healthChecks().register("database", new DatabaseHealthCheck(configuration.getDatabase()));

    environment.admin().addTask(new DbShutdownTask(configuration.getDatabase()));
  }

  private Injector createInjector(final EnergyManagerConfiguration configuration, final Environment environment) {
    return Guice.createInjector(
        createGuiceModule(configuration, environment),
        createJpaPersistModule(configuration.getDatabase())
    );
  }

  protected Module createGuiceModule(final EnergyManagerConfiguration configuration, final Environment environment) {
    return new AbstractModule() {
      @Override
      protected void configure() {
        // TODO bind dropwizard components (i.e. configuration tree and environment validator) to prevent them from
        // being instantiated twice (by drop wizard and google guice)
        bind(EnergyManagerConfiguration.class).toInstance(configuration);
        bind(DatabaseConfiguration.class).toInstance(configuration.getDatabase());
        bind(Validator.class).toInstance(environment.getValidator());
      }
    };
  }

}
