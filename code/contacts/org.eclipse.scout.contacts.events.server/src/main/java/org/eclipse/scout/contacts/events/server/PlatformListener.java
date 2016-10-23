/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.events.server;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.contacts.events.server.sql.SQLs;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.events.shared.person.PersonTablePageDataExtension;
import org.eclipse.scout.contacts.server.sql.DatabaseProperties;
import org.eclipse.scout.contacts.server.sql.DatabaseSetupService;
import org.eclipse.scout.contacts.server.sql.IDataStoreService;
import org.eclipse.scout.contacts.server.sql.SuperUserRunContextProducer;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(20)
public class PlatformListener implements IPlatformListener, IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(PlatformListener.class);

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
      registerExtensions();
    }
  }

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoCreateProperty.class)) {
      try {
        RunContext context = BEANS.get(SuperUserRunContextProducer.class).produce();
        IRunnable runnable = new IRunnable() {

          @Override
          public void run() throws Exception {
            createEventTable();
            createParticipantTable();
          }
        };

        context.run(runnable);
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  private void registerExtensions() {
    IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

    // Register DTO core extensions
    extensionRegistry.register(PersonTablePageDataExtension.class);
    extensionRegistry.register(PersonFormTabExtensionData.class);
  }

  public void createEventTable() {
    if (!getExistingTables().contains("EVENT")) {
      SQL.insert(SQLs.EVENT_CREATE_TABLE);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        createEventEntry("Big Main Circumstances", DateUtility.parse("09.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("12.03.2015 16:45", "dd.MM.yyyy HH:mm"), "San Francisco", "US", null);
        createEventEntry("Eventurist Delight", DateUtility.parse("24.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("26.03.2015 17:00", "dd.MM.yyyy HH:mm"), "Bruehl", "DE", null);
        createEventEntry("Eventage N Event", DateUtility.parse("02.11.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("05.11.2015 17:00", "dd.MM.yyyy HH:mm"), "Ludwigsburg", "DE", null);
        createEventEntry("Prince Go Occasions", null, null, "Shire", "NZ", null);

        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  private void createEventEntry(String title, Date starts, Date ends, String city, String country, String url) {
    SQL.insert(SQLs.EVENT_INSERT_SAMPLE_DATA, new NVPair("eventId", UUID.randomUUID().toString()),
        new NVPair("title", title),
        new NVPair("starts", starts),
        new NVPair("ends", ends),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("url", url));
  }

  protected void createParticipantTable() {
    if (!getExistingTables().contains("PARTICIPANT")) {
      SQL.insert(SQLs.PARTICIPANT_CREATE_TABLE);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        createParticipantEntry("Eventage N Event", DatabaseSetupService.PERSON01);
        createParticipantEntry("Eventage N Event", DatabaseSetupService.PERSON16);
        createParticipantEntry("Eventage N Event", DatabaseSetupService.PERSON02);
        createParticipantEntry("Eventurist Delight", DatabaseSetupService.PERSON02);
        createParticipantEntry("Eventurist Delight", DatabaseSetupService.PERSON19);
        createParticipantEntry("Prince Go Occasions", DatabaseSetupService.PERSON16);
        createParticipantEntry("Prince Go Occasions", DatabaseSetupService.PERSON02);
        createParticipantEntry("Prince Go Occasions", DatabaseSetupService.PERSON03);
        createParticipantEntry("Prince Go Occasions", DatabaseSetupService.PERSON13);
        createParticipantEntry("Eventage N Event", DatabaseSetupService.PERSON07);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON11);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON14);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON16);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON09);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON18);
        createParticipantEntry("Big Main Circumstances", DatabaseSetupService.PERSON20);

        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }

  private void createParticipantEntry(String eventTitle, String personId) {
    SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE_DATA,
        new NVPair("eventTitle", eventTitle),
        new NVPair("personId", personId));
  }

  private Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  @Override
  public void dropDataStore() {
    SQL.update(SQLs.PARTICIPANT_DROP_TABLE);
    SQL.update(SQLs.EVENT_DROP_TABLE);
  }

  @Override
  public void createDataStore() {
    createEventTable();
    createParticipantTable();
  }
}
