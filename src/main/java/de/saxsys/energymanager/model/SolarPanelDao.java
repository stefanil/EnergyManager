package de.saxsys.energymanager.model;

import static java.util.stream.Collectors.toList;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.MonitoringEntry;
import de.saxsys.energymanager.api.MonitoringEntry.Weather;
import de.saxsys.energymanager.api.SolarPanel;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class SolarPanelDao {

  private static final Logger LOG = LoggerFactory.getLogger(SolarPanelDao.class);

  /* **************** dirty singleton **************** */

//  private static SolarPanelDao instance;
//
//  public static SolarPanelDao getInstance() {
//    if (instance == null) {
//      instance = new SolarPanelDao();
//    }
//    return instance;
//  }

  /* ************************************************* */

  private final Provider<EntityManager> entityManager;
  private ModelMapper modelMapper;

  @Inject
  public SolarPanelDao(final Provider<EntityManager> entityManager) {
    this.entityManager = entityManager;
    modelMapper = new ModelMapper();
  }

  public void addSolarPanel(final SolarPanel solarPanel) {
    final SolarPanelEntity solarPanelEntity = modelMapper.map(solarPanel, SolarPanelEntity.class);
    entityManager.get().persist(solarPanelEntity);
  }

  public boolean isMonitored(final long id) {
    return findSolarPanel(id) != null;
  }

  public SolarPanel findSolarPanel(final long id) {
    final SolarPanelEntity solarPanelEntity = entityManager.get().find(SolarPanelEntity.class, id);
    if (solarPanelEntity == null) {
      LOG.warn("Solar panel entity with id {} was not found.", id);
      return null;
    }

    return modelMapper.map(solarPanelEntity, SolarPanel.class);
  }

  public MonitoringData generateMonitoringData(final long id, final int days) {
    final SolarPanel solarPanel = findSolarPanel(id);
    if (solarPanel == null) {
      LOG.warn("Cannot extract monitoring data for unknown solar panel with id {}", id);
      return null;
    }

    return new MonitoringData(solarPanel, generateEntries(days));
  }

  public List<SolarPanel> findAllSolarPanels() {
    return entityManager.get().createQuery("select s from solar_panel s", SolarPanelEntity.class)
        .getResultList().stream()
        .map(solarPanelEntity -> modelMapper.map(solarPanelEntity, SolarPanel.class))
        .collect(toList());
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
