package de.saxsys.energymanager.model;

import com.google.common.base.MoreObjects;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Solar JPA entity representing a solar panel.
 */
@Entity(name = "solar_panel")
public class SolarPanelEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotEmpty
  private String name;

  public SolarPanelEntity() {
  }

  public SolarPanelEntity(final Long id, final String name) {
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
    if (!(o instanceof SolarPanelEntity)) {
      return false;
    }
    final SolarPanelEntity that = (SolarPanelEntity) o;
    return id == null && that.id == null ? false : Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return id == null ? System.identityHashCode(this) : Objects.hash(id);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }

}
