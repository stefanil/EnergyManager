package de.saxsys.energymanager.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

/**
 * Main Configuration class for energy manager application.
 */
public class EnergyManagerConfiguration extends Configuration {

  @NotNull
  @Valid
  private DatabaseConfiguration database;

  public DatabaseConfiguration getDatabase() {
    return database;
  }

  public void setDatabase(final DatabaseConfiguration database) {
    this.database = database;
  }
}
