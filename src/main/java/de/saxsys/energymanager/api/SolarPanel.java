package de.saxsys.energymanager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class SolarPanel {

  private Long id;

  @NotNull
  @NotEmpty
  private String name;

  public SolarPanel() {
  }

  public SolarPanel(
      @JsonProperty("id") final Long id,
      @JsonProperty("name") final String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SolarPanel)) {
      return false;
    }
    final SolarPanel that = (SolarPanel) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }
}
