package de.saxsys.energymanager.util;

import static com.google.common.base.Strings.nullToEmpty;

import de.saxsys.energymanager.configuration.DatabaseConfiguration;

import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for db operations.
 */
public class DbUtil {

  private static final Logger LOG = LoggerFactory.getLogger(DbUtil.class);

  private static final int CONNECTION_TIME_OUT = 10;  // seconds

  private static Connection CONNECTION;

  public static boolean isConnected(final DatabaseConfiguration databaseConfiguration) throws SQLException {
    final Boolean result = runOnJDBCConnection(databaseConfiguration, connection ->
        connection.isValid(CONNECTION_TIME_OUT)
    );

    return result != null ? result : false;
  }

  public static Module createJpaPersistModule(final DatabaseConfiguration databaseConfiguration) {
    final JpaPersistModule jpaModule = new JpaPersistModule("Default");
    jpaModule.properties(getJPAConnectionProperties(databaseConfiguration));

    return jpaModule;
  }

  public static void shutdown(final DatabaseConfiguration databaseConfiguration) {
    runOnJDBCConnection(databaseConfiguration, connection -> {
      LOG.info("shutting down database...");
      connection.close();

      return null;
    });
  }

  private static Properties getJPAConnectionProperties(final DatabaseConfiguration databaseConfiguration) {
    final Properties properties = new Properties();
    properties.put("javax.persistence.jdbc.driver", nullToEmpty(databaseConfiguration.getDriverClass()));
    properties.put("javax.persistence.jdbc.user", nullToEmpty(databaseConfiguration.getUser()));
    properties.put("javax.persistence.jdbc.password", nullToEmpty(databaseConfiguration.getPassword()));
    properties.put("javax.persistence.jdbc.url", nullToEmpty(databaseConfiguration.getUrl()));
    properties.put("hibernate.hbm2ddl.auto", nullToEmpty(databaseConfiguration.getMode()));
    properties.put("hibernate.dialect", nullToEmpty(databaseConfiguration.getDialect()));

    return properties;
  }

  private static <T> T runOnJDBCConnection(final DatabaseConfiguration databaseConfiguration,
      final ThrowingFunction<Connection, T, SQLException> function) {
    try {
      if (CONNECTION == null) {
        CONNECTION = DriverManager.getConnection(
            databaseConfiguration.getUrl(),
            getJDBCConnectionProperties(databaseConfiguration));
      }

      return function.apply(CONNECTION);
    } catch (final SQLException exc) {
      LOG.error("SQL error on database operation.", exc);
    }

    return null;
  }

  private static Properties getJDBCConnectionProperties(final DatabaseConfiguration databaseConfiguration) {
    final Properties connectionProps = new Properties();
    connectionProps.put("user", nullToEmpty(databaseConfiguration.getUser()));
    connectionProps.put("password", nullToEmpty(nullToEmpty(databaseConfiguration.getPassword())));

    return connectionProps;
  }

  public static Integer countCustomers(final DatabaseConfiguration databaseConfiguration) {
    return runOnJDBCConnection(databaseConfiguration, connection -> {
      try (
          final Statement stmt = connection.createStatement();
          final ResultSet rs = stmt.executeQuery("select count(*) as s from solar_panel")
      ) {
        if (rs.next()) {
          return rs.getInt("s");
        }
      }

      return 0;
    });
  }

}
