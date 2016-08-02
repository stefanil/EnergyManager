package de.saxsys.energymanager.resources;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Integration test using the production configuration.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SolarPanelsIT {

  // TODO II_ii_b DropWizardAppRule

  private static SolarPanelsResourceClient CLIENT;

  @BeforeClass
  public static void setUp() throws Exception {
    // TODO II_ii_b instantiate SolarPanelsResourceClient
  }

  @Test
  public void step01ASolarPanelShouldBeCreated() throws Exception {
    // TODO II_ii_b use client rule for assertions (see SolarPanelsResourceTest)
  }

  @Test
  public void step02ASolarPanelShouldBeMonitored() throws Exception {
    // TODO II_ii_b use client rule for assertions (see SolarPanelsResourceTest)
  }

  @Test
  public void step03ASolarPanelMonitoringThrowsNotFoundWhenSolarPanelDoesNotExist() throws Exception {
    // TODO II_ii_b use client rule for assertions (see SolarPanelsResourceTest)
  }

  @Test
  public void step04ASolarPanelMonitoringThrowsBadRequestWhenDaysIsANegativeNumber() throws Exception {
    // TODO II_ii_b use client rule for assertions (see SolarPanelsResourceTest)
  }

  @Test
  public void step05ShouldGetSolarPanels() throws Exception {
    // TODO II_ii_b use client rule for assertions (see SolarPanelsResourceTest)
  }
}
