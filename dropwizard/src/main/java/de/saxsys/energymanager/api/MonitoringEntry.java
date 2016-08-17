package de.saxsys.energymanager.api;

import static com.google.common.collect.Lists.newArrayList;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MonitoringEntry {

  @Min(0)
  @Max(100)
  private long generatorPower;  // watts

  public MonitoringEntry(
      @JsonProperty("generatorPower") final long generatorPower) {
    this.generatorPower = generatorPower;
  }

  public long getGeneratorPower() {
    return generatorPower;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MonitoringEntry)) {
      return false;
    }
    final MonitoringEntry that = (MonitoringEntry) o;
    return generatorPower == that.generatorPower;
  }

  @Override
  public int hashCode() {
    return Objects.hash(generatorPower);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("generatorPower", generatorPower)
        .toString();
  }

  public static List<MonitoringEntry> fromWeatherForADay(final Weather weather) {
    final List<Long> result;

    switch (weather) {
      case CLOUDY:
        result = newArrayList(0L, 1L, 0L, 2L, 3L, 9L, 7L, 12L, 15L, 18L, 21L, 24L,
            22L, 19L, 21L, 17L, 14L, 15L, 13L, 15L, 9L, 6L, 3L, 1L);
        break;
      case MIXED:
        result = newArrayList(1L, 4L, 2L, 0L, 5L, 8L, 9L, 15L, 19L, 20L, 27L, 29L,
            35L, 34L, 37L, 36L, 23L, 21L, 24L, 18L, 10L, 7L, 3L, 2L);
        break;
      default:
      case SHINY:
        result = newArrayList(4L, 6L, 2L, 8L, 13L, 23L, 25L, 27L, 34L, 68L, 72L, 95L,
            99L, 92L, 80L, 74L, 58L, 64L, 32L, 16L, 14L, 8L, 5L, 3L);
        break;
    }

    return result.stream()
        .map(MonitoringEntry::new)
        .collect(toList());
  }

  public enum Weather {
    CLOUDY(0), MIXED(1), SHINY(2);

    private final int ordinal;

    private Weather(final int ordinal) {
      this.ordinal = ordinal;
    }

    public static Weather fromOrdinal(final int ordinal) {
      return Arrays.stream(Weather.values())
          .filter(weather -> weather.ordinal() == ordinal)
          .findFirst()
          .orElse(SHINY);
    }
  }
}
