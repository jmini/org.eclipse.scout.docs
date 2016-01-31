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
package org.eclipse.scout.contacts.server.module.events;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.contacts.server.SuperUserRunContextProducer;
import org.eclipse.scout.contacts.server.module.events.sql.SQLs;
import org.eclipse.scout.contacts.server.sql.DBSetupService;
import org.eclipse.scout.contacts.shared.module.events.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.shared.module.events.person.PersonTablePageDataExtension;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
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
public class PlatformListener implements IPlatformListener {

  private static final Logger LOG = LoggerFactory.getLogger(PlatformListener.class);

  private static final UUID EVENT1 = UUID.randomUUID();
  private static final UUID EVENT2 = UUID.randomUUID();
  private static final UUID EVENT3 = UUID.randomUUID();
  private static final UUID EVENT4 = UUID.randomUUID();

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
      registerExtensions();
    }
  }

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoCreateProperty.class)) {
      try {
        BEANS.get(SuperUserRunContextProducer.class).produce().run(new IRunnable() {

          @Override
          public void run() throws Exception {
            Set<String> tables = getExistingTables();
            createEventTable(tables);
            createParticipantTable(tables);
          }
        });
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

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createEventTable(Set<String> tables) {
    if (!tables.contains("EVENT")) {
      SQL.insert(SQLs.EVENT_CREATE_TABLE);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createEventEntry(EVENT1, "Big Main Circumstances", DateUtility.parse("09.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("12.03.2015 16:45", "dd.MM.yyyy HH:mm"), "San Francisco", "US", "https://www.eclipsecon.org/na2015/");
        createEventEntry(EVENT2, "Eventurist Delight", DateUtility.parse("24.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("26.03.2015 17:00", "dd.MM.yyyy HH:mm"), "Bruehl", "DE", "http://www.javaland.eu/javaland-2015/");
        createEventEntry(EVENT3, "Eventage N Event", DateUtility.parse("02.11.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("05.11.2015 17:00", "dd.MM.yyyy HH:mm"), "Ludwigsburg", "DE", "https://www.eclipsecon.org/europe2015/");
        createEventEntry(EVENT4, "Prince Go Occasions", null, null, "Shire", "NZ", null);

        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  private void createEventEntry(UUID uuid, String title, Date starts, Date ends, String city, String country, String url) {
    SQL.insert(SQLs.EVENT_INSERT_SAMPLE_DATA, new NVPair("eventId", uuid.toString()),
        new NVPair("title", title),
        new NVPair("starts", starts),
        new NVPair("ends", ends),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("url", url));
  }

  private void createParticipantTable(Set<String> tables) {
    if (!tables.contains("PARTICIPANT")) {
      SQL.insert(SQLs.PARTICIPANT_CREATE_TABLE);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createParticipantEntry(EVENT3, DBSetupService.PERSON01);
        createParticipantEntry(EVENT3, DBSetupService.PERSON16);
        createParticipantEntry(EVENT3, DBSetupService.PERSON02);
        createParticipantEntry(EVENT2, DBSetupService.PERSON02);
        createParticipantEntry(EVENT2, DBSetupService.PERSON19);
        createParticipantEntry(EVENT4, DBSetupService.PERSON16);
        createParticipantEntry(EVENT4, DBSetupService.PERSON02);
        createParticipantEntry(EVENT4, DBSetupService.PERSON03);
        createParticipantEntry(EVENT4, DBSetupService.PERSON13);
        createParticipantEntry(EVENT3, DBSetupService.PERSON07);
        createParticipantEntry(EVENT1, DBSetupService.PERSON11);
        createParticipantEntry(EVENT1, DBSetupService.PERSON14);
        createParticipantEntry(EVENT1, DBSetupService.PERSON16);
        createParticipantEntry(EVENT1, DBSetupService.PERSON09);
        createParticipantEntry(EVENT1, DBSetupService.PERSON18);
        createParticipantEntry(EVENT1, DBSetupService.PERSON20);

        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }

  private void createParticipantEntry(UUID eventUuid, UUID personUuid) {
    SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE_DATA,
        new NVPair("eventUuid", eventUuid.toString()),
        new NVPair("personUuid", personUuid.toString()));
  }
}
