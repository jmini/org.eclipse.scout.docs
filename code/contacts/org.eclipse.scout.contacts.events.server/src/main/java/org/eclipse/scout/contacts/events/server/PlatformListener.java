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

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.contacts.events.server.datasource.DatastoreEvent;
import org.eclipse.scout.contacts.events.server.datasource.DatastorePersonChooser;
import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.person.PersonChooserFormData;
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
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(20)
public class PlatformListener implements IPlatformListener, IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(PlatformListener.class);
  private static final UUID EVENT01 = UUID.randomUUID();
  private static final UUID EVENT02 = UUID.randomUUID();
  private static final UUID EVENT03 = UUID.randomUUID();
  private static final UUID EVENT04 = UUID.randomUUID();

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
      DatastoreEvent datastore = BEANS.get(DatastoreEvent.class);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        datastore.store(createEventFormData(EVENT01, "Big Main Circumstances", DateUtility.parse("09.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("12.03.2015 16:45", "dd.MM.yyyy HH:mm"), "San Francisco", "US", null));
        datastore.store(createEventFormData(EVENT02, "Eventurist Delight", DateUtility.parse("24.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("26.03.2015 17:00", "dd.MM.yyyy HH:mm"), "Bruehl", "DE", null));
        datastore.store(createEventFormData(EVENT03, "Eventage N Event", DateUtility.parse("02.11.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("05.11.2015 17:00", "dd.MM.yyyy HH:mm"), "Ludwigsburg", "DE", null));
        datastore.store(createEventFormData(EVENT04, "Prince Go Occasions", null, null, "Shire", "NZ", null));
        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  private EventFormData createEventFormData(UUID eventId, String title, Date starts, Date ends, String city, String country, String url) {
    EventFormData formData = new EventFormData();
    formData.setEventId(eventId.toString());
    formData.getTitle().setValue(title);
    formData.getStarts().setValue(starts);
    formData.getEnds().setValue(ends);
    formData.getLocationBox().getCity().setValue(city);
    formData.getLocationBox().getCountry().setValue(country);
    formData.getHomepage().setValue(url);
    return formData;
  }

  protected void createParticipantTable() {
    if (!getExistingTables().contains("PARTICIPANT")) {
      DatastorePersonChooser datastore = BEANS.get(DatastorePersonChooser.class);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        datastore.store(createParticipantFormData(EVENT03, DatabaseSetupService.PERSON01));
        datastore.store(createParticipantFormData(EVENT03, DatabaseSetupService.PERSON16));
        datastore.store(createParticipantFormData(EVENT03, DatabaseSetupService.PERSON02));
        datastore.store(createParticipantFormData(EVENT02, DatabaseSetupService.PERSON02));
        datastore.store(createParticipantFormData(EVENT02, DatabaseSetupService.PERSON19));
        datastore.store(createParticipantFormData(EVENT04, DatabaseSetupService.PERSON16));
        datastore.store(createParticipantFormData(EVENT04, DatabaseSetupService.PERSON02));
        datastore.store(createParticipantFormData(EVENT04, DatabaseSetupService.PERSON03));
        datastore.store(createParticipantFormData(EVENT04, DatabaseSetupService.PERSON13));
        datastore.store(createParticipantFormData(EVENT03, DatabaseSetupService.PERSON07));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON11));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON14));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON16));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON09));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON18));
        datastore.store(createParticipantFormData(EVENT01, DatabaseSetupService.PERSON20));
        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }

  private PersonChooserFormData createParticipantFormData(UUID eventId, UUID personId) {
    PersonChooserFormData formData = new PersonChooserFormData();
    formData.setEventId(eventId.toString());
    formData.getPerson().setValue(personId.toString());
    return formData;
  }

  private Set<String> getExistingTables() {
    return Collections.emptySet();
  }

  @Override
  public void dropDataStore() {
    BEANS.get(DatastorePersonChooser.class).clear();
    BEANS.get(DatastoreEvent.class).clear();
  }

  @Override
  public void createDataStore() {
    createEventTable();
    createParticipantTable();
  }
}
