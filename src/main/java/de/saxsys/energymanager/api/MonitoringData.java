 package de.saxsys.energymanager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class MonitoringData {

  @NotNull
  private final SolarPanel solarPanel;
  @NotNull
  private final List<MonitoringEntry> entries;

  public MonitoringData(
      @JsonProperty("solarPanel") final SolarPanel solarPanel,
      @JsonProperty("entries") final List<MonitoringEntry> entries) {
    this.solarPanel = solarPanel;
    this.entries = entries;
  }

  public SolarPanel getSolarPanel() {
    return solarPanel;
  }

  public List<MonitoringEntry> getEntries() {
    return entries;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MonitoringData)) {
      return false;
    }
    final MonitoringData that = (MonitoringData) o;
    return Objects.equals(solarPanel, that.solarPanel) &&
        Objects.equals(entries, that.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(solarPanel, entries);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("solarPanel", solarPanel)
        .add("entries", entries)
        .toString();
  }
}
