/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package de.saxsys.energymanager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class SolarPanel {

  @NotNull
  @NotEmpty
  private String name;

  public SolarPanel() {
  }

  public SolarPanel(@JsonProperty("name") final String name) {
    this.name = name;
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
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .toString();
  }

}
