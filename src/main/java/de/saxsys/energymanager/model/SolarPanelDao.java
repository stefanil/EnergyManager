package de.saxsys.energymanager.model;

import de.saxsys.energymanager.api.SolarPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolarPanelDao {

  private static final Logger LOG = LoggerFactory.getLogger(SolarPanelDao.class);

  /* **************** dirty singleton **************** */

  private static SolarPanelDao instance;

  public static SolarPanelDao getInstance() {
    if (instance == null) {
      instance = new SolarPanelDao();
    }
    return instance;
  }

  /* ************************************************* */

  public void addSolarPanel(final SolarPanel solarPanel) {
    // just a mock for now
  }

}
