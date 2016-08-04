package de.saxsys.energymanager.model;

import de.saxsys.energymanager.api.SolarPanel;

public class SolarPanelDao {
  
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
