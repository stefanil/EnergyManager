/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package de.saxsys.energymanager.db;

import de.saxsys.energymanager.DatabaseConfiguration;

import com.google.common.collect.ImmutableMultimap;

import java.io.PrintWriter;

import io.dropwizard.servlets.tasks.Task;

/**
 * Runtime task for shutting down database.
 */
public class DbShutdownTask extends Task {

  private final DatabaseConfiguration databaseConfiguration;

  public DbShutdownTask(final DatabaseConfiguration databaseConfiguration) {
    super("dbShutdown");
    this.databaseConfiguration = databaseConfiguration;
  }

  @Override
  public void execute(final ImmutableMultimap<String, String> parameters, final PrintWriter output) throws Exception {
    DbUtil.shutdown(databaseConfiguration);
  }
}
