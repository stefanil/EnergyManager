package de.saxsys.energymanager.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.saxsys.energymanager.api.MonitoringData;
import de.saxsys.energymanager.api.SolarPanel;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * Unit test for {@link SolarPanelDao}.
 */
public class SolarPanelDaoTest {

  private SolarPanelDao cut;

  private EntityManager em = mock(EntityManager.class);

  @Before
  public void setUp() throws Exception {
    cut = new SolarPanelDao(() -> em);
  }

  @Test
  public void addingASolarPanelShouldWriteToDb() throws Exception {
    final String name = "aPanel";
    doNothing().when(em).persist(isA(SolarPanelEntity.class));
    doAnswer(invocation -> {
      final SolarPanelEntity solarPanelEntity = invocation.getArgument(0);
      assertThat(solarPanelEntity.getName()).isEqualTo(name);
      assertThat(solarPanelEntity.getId()).isNull();

      return null;
    }).when(em).persist(isA(SolarPanelEntity.class));

    cut.addSolarPanel(new SolarPanel(null, name));

    verify(em).persist(isA(SolarPanelEntity.class));
  }

  @Test
  public void anExistingSolarPanelShouldBeFound() throws Exception {
    final Long id = 1L;
    final String name = "aPanel";
    when(em.find(SolarPanelEntity.class, id)).thenReturn(new SolarPanelEntity(id, name));

    final SolarPanel solarPanel = cut.findSolarPanel(id);

    assertThat(solarPanel.getId()).isEqualTo(id);
    assertThat(solarPanel.getName()).isEqualTo(name);
  }

  @Test
  public void findingANonExistingSolarShouldReturnNull() throws Exception {
    final Long id = 1L;
    when(em.find(SolarPanelEntity.class, id)).thenReturn(null);

    final SolarPanel solarPanel = cut.findSolarPanel(id);

    assertThat(solarPanel).isNull();
  }

  @Test
  public void allExistingSolarPanelsShouldBeMonitored() throws Exception {
    when(em.find(SolarPanelEntity.class, 1L)).thenReturn(new SolarPanelEntity(1L, "aPanel1"));
    when(em.find(SolarPanelEntity.class, 2L)).thenReturn(null);

    assertThat(cut.isMonitored(1L)).isTrue();
    assertThat(cut.isMonitored(2L)).isFalse();
  }

  @Test
  public void monitoringDataShouldBeGeneratedForExistingSolarPanel() throws Exception {
    final long id = 1L;
    final String name = "aPanel1";
    when(em.find(SolarPanelEntity.class, id)).thenReturn(new SolarPanelEntity(id, name));

    final MonitoringData monitoringData = cut.generateMonitoringData(id, 2);

    assertThat(monitoringData.getSolarPanel().getId()).isEqualTo(id);
    assertThat(monitoringData.getSolarPanel().getName()).isEqualTo(name);
    assertThat(monitoringData).isNotNull();
    assertThat(monitoringData.getEntries()).hasSize(48);
  }

  @Test
  public void monitoringDataShouldNotBeGeneratedForExistingSolarPanel() throws Exception {
    when(em.find(SolarPanelEntity.class, 1L)).thenReturn(null);

    final MonitoringData monitoringData = cut.generateMonitoringData(1L, 2);

    assertThat(monitoringData).isNull();
  }

}
