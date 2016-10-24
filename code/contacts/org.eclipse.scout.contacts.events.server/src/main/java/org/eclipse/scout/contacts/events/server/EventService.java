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

import java.util.UUID;

import org.eclipse.scout.contacts.events.server.datasource.DatastoreEvent;
import org.eclipse.scout.contacts.events.shared.event.CreateEventPermission;
import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.event.EventTablePageData;
import org.eclipse.scout.contacts.events.shared.event.IEventService;
import org.eclipse.scout.contacts.events.shared.event.ReadEventPermission;
import org.eclipse.scout.contacts.events.shared.event.UpdateEventPermission;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class EventService implements IEventService {

  @Override
  public EventTablePageData getTableData(SearchFilter filter, String organizationId) {
    EventTablePageData pageData = new EventTablePageData();

    DatastoreEvent datastore = BEANS.get(DatastoreEvent.class);
    datastore.loadAllInto(pageData, organizationId);

    return pageData;
  }

  @Override
  public EventFormData create(EventFormData formData) {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getEventId())) {
      formData.setEventId(UUID.randomUUID().toString());
    }

    DatastoreEvent datastore = BEANS.get(DatastoreEvent.class);
    datastore.store(formData);

    return store(formData);
  }

  @Override
  public EventFormData load(EventFormData formData) {
    if (!ACCESS.check(new ReadEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    DatastoreEvent datastore = BEANS.get(DatastoreEvent.class);
    return datastore.load(formData);
  }

  @Override
  public EventFormData prepareCreate(EventFormData formData) {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    return formData;
  }

  @Override
  public EventFormData store(EventFormData formData) {
    if (!ACCESS.check(new UpdateEventPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

//    SQL.update(SQLs.EVENT_UPDATE, formData);
//
//    TableBeanHolderFilter deletedParticipants = new TableBeanHolderFilter(formData.getParticipantTableField(), ITableHolder.STATUS_DELETED);
//    TableBeanHolderFilter insertedParticipants = new TableBeanHolderFilter(formData.getParticipantTableField(), ITableHolder.STATUS_INSERTED);
//    NVPair eventId = new NVPair("eventId", formData.getEventId());
//
//    SQL.delete(SQLs.EVENT_PARTICIPANTS_DELETE, deletedParticipants, eventId);
//    SQL.insert(SQLs.EVENT_PARTICIPANTS_INSERT, insertedParticipants, eventId);

    return formData;
  }
}
