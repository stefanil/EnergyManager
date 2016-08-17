package de.saxsys.energymanager.configuration;

import javax.validation.constraints.NotNull;

/**
 * Configuration of database.
 */
public class DatabaseConfiguration {

  @NotNull
  private String driverClass;
  @NotNull
  private String user;
  private String password;
  @NotNull
  private String url;
  @NotNull
  private String mode;
  @NotNull
  private String dialect;

  public String getDriverClass() {
    return driverClass;
  }

  public void setDriverClass(final String driverClass) {
    this.driverClass = driverClass;
  }

  public String getUser() {
    return user;
  }

  public void setUser(final String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(final String mode) {
    this.mode = mode;
  }

  public String getDialect() {
    return dialect;
  }

  public void setDialect(final String dialect) {
    this.dialect = dialect;
  }


}
