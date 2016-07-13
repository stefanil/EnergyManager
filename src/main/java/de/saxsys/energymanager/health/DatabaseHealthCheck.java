package de.saxsys.energymanager.health;

import static de.saxsys.energymanager.util.DbUtil.isConnected;

import de.saxsys.energymanager.configuration.DatabaseConfiguration;

import com.codahale.metrics.health.HealthCheck;

import java.util.Objects;

public class DatabaseHealthCheck extends HealthCheck {

  private final DatabaseConfiguration database;

  public DatabaseHealthCheck(DatabaseConfiguration database) {
    this.database = database;
  }

  @Override
  protected Result check() throws Exception {
    if (isConnected(database)) {
      return Result.healthy();
    } else {
      return Result.unhealthy("Cannot connect to database.");
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DatabaseHealthCheck)) {
      return false;
    }
    final DatabaseHealthCheck that = (DatabaseHealthCheck) o;
    return Objects.equals(database, that.database);
  }

  @Override
  public int hashCode() {
    return Objects.hash(database);
  }
}
