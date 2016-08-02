package de.saxsys.energymanager.model;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.MonitoringEntry;
import de.saxsys.energymanager.api.MonitoringEntry.Weather;
import de.saxsys.energymanager.api.SolarPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SolarPanelDao {

  /* **************** dirty singleton **************** */

  private static SolarPanelDao instance;

  public static SolarPanelDao getInstance() {
    if (instance == null) {
      instance = new SolarPanelDao();
    }
    return instance;
  }

  private List<SolarPanel> solarPanels = Collections.emptyList();

  /* ************************************************* */

  public void addSolarPanel(final SolarPanel solarPanel) {
    solarPanels.add(solarPanel);
  }

  public boolean isMonitored(final long id) {
    return findSolarPanel(id) != null;
  }

  public SolarPanel findSolarPanel(final long id) {
    return solarPanels.stream()
        .filter(solarPanel -> solarPanel.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public MonitoringData generateMonitoringData(final long id, final int days) {
    final SolarPanel solarPanel = findSolarPanel(id);
    if (solarPanel == null) {
      return null;
    }

    return new MonitoringData(solarPanel, generateEntries(days));
  }

  public List<SolarPanel> findAllSolarPanels() {
    return solarPanels;
  }

  private ArrayList<MonitoringEntry> generateEntries(final int days) {
    final ArrayList<MonitoringEntry> result = new ArrayList<>();

    for (int day = 1; day <= days; day++) {
      result.addAll(
          MonitoringEntry.fromWeatherForADay(
              Weather.fromOrdinal(ThreadLocalRandom.current().nextInt(0, 3))));
    }

    return result;
  }
}
