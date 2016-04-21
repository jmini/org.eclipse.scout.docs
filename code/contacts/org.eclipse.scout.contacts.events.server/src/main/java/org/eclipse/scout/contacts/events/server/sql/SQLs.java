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
package org.eclipse.scout.contacts.events.server.sql;

public interface SQLs {

  String SELECT_TABLE_NAMES = ""
      + "SELECT       UPPER(tablename) "
      + "FROM         sys.systables "
      + "INTO         :result";

  String EVENT_CREATE_TABLE = ""
      + "CREATE       "
      + "TABLE        EVENT "
      + "            (event_id VARCHAR(64) NOT NULL CONSTRAINT EVENT_PK PRIMARY KEY, "
      + "             title VARCHAR(64), "
      + "             date_start TIMESTAMP, "
      + "             date_end TIMESTAMP, "
      + "             city VARCHAR(64), "
      + "             country VARCHAR(2), "
      + "             url VARCHAR(64), "
      + "             comment VARCHAR(1024))";

  String EVENT_INSERT_SAMPLE_DATA = ""
      + "INSERT       "
      + "INTO         EVENT "
      + "            (event_id, "
      + "             title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url) "
      + "VALUES      (:eventUuid, "
      + "             :title, "
      + "             :starts, "
      + "             :ends, "
      + "             :city, "
      + "             :country, "
      + "             :url)";

  String PARTICIPANT_CREATE_TABLE = ""
      + "CREATE       "
      + "TABLE        PARTICIPANT "
      + "             (event_id VARCHAR(64) NOT NULL, "
      + "              person_id VARCHAR(64) NOT NULL, "
      + "PRIMARY KEY  (event_id, person_id))";

  String PARTICIPANT_INSERT_SAMPLE_DATA = ""
      + "INSERT       "
      + "INTO         PARTICIPANT "
      + "            (event_id, "
      + "             person_id) "
      + "VALUES      (:eventUuid, "
      + "             :personUuid)";

  String PERSON_EVENT_SELECT = ""
      + "SELECT       e.event_id, "
      + "             e.title, "
      + "             e.date_start, "
      + "             e.city, "
      + "             e.country "
      + "FROM         PARTICIPANT p "
      + "LEFT JOIN    EVENT e "
      + "ON           e.event_id = p.event_id "
      + "WHERE        p.person_id = :personId "
      + "INTO         :{events.id}, "
      + "             :{events.title}, "
      + "             :{events.starts}, "
      + "             :{events.city}, "
      + "             :{events.country}";

  String EVENT_PAGE_DATA_SELECT = ""
      + "SELECT       e.event_id, "
      + "             e.title, "
      + "             e.date_start, "
      + "             e.date_end, "
      + "             e.city, "
      + "             e.country, "
      + "             e.url, "
      + "             (SELECT   COUNT(1) "
      + "             FROM     PARTICIPANT p "
      + "             WHERE    P.event_id = e.event_id) "
      + "FROM         EVENT e";

  String EVENT_PAGE_DATA_WHERE_CLAUSE = ""
      + "AND          e.event_id in (SELECT DISTINCT p.event_id "
      + "                            FROM    PARTICIPANT p, "
      + "                                    PERSON c "
      + "                            WHERE   p.person_id = c.person_id "
      + "                            AND     organization_id = :organizationId)";

  String EVENT_PAGE_DATA_INTO = ""
      + "INTO         :{page.eventId}, "
      + "             :{page.title}, "
      + "             :{page.starts}, "
      + "             :{page.ends}, "
      + "             :{page.city}, "
      + "             :{page.country}, "
      + "             :{page.homepage}, "
      + "             :{page.participants}";

  String EVENT_INSERT = ""
      + "INSERT     INTO "
      + "EVENT      (event_id) "
      + "VALUES     (:eventId)";

  String EVENT_SELECT = ""
      + "SELECT       title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url, "
      + "             comment "
      + "FROM         EVENT "
      + "WHERE        event_id = :eventId "
      + "INTO         :title, "
      + "             :starts, "
      + "             :ends, "
      + "             :locationBox.city, "
      + "             :locationBox.country, "
      + "             :homepage, "
      + "             :comments";

  String EVENT_UPDATE = ""
      + "UPDATE       EVENT "
      + "SET          title = :title, "
      + "             date_start = :starts, "
      + "             date_end = :ends, "
      + "             url = :homepage, "
      + "             city = :locationBox.city, "
      + "             country = :locationBox.country, "
      + "             comment = :comments "
      + "WHERE        event_id = :eventId";

  String EVENT_PARTICIPANTS_SELECT = ""
      + "SELECT       c.person_id, "
      + "             c.first_name, "
      + "             c.last_name, "
      + "             c.organization_id "
      + "FROM         PARTICIPANT p "
      + "LEFT JOIN    PERSON c "
      + "ON           c.person_id = p.person_id "
      + "WHERE        p.event_id = :eventId "
      + "INTO         :{participantTableField.personId}, "
      + "             :{participantTableField.firstName}, "
      + "             :{participantTableField.lastName}, "
      + "             :{participantTableField.organization}";

  String EVENT_PARTICIPANTS_DELETE = ""
      + "DELETE       FROM PARTICIPANT "
      + "WHERE        event_id = :eventId "
      + "AND          person_id = :{personId}";

  String EVENT_PARTICIPANTS_INSERT = ""
      + "INSERT       INTO "
      + "PARTICIPANT  (event_id, "
      + "              person_id) "
      + "VALUES       (:eventId, "
      + "              :{personId})";

  String EVENT_COUNT_BY_PERSON = ""
      + "SELECT       person_id, "
      + "             COUNT(event_id) "
      + "FROM         PARTICIPANT "
      + "GROUP BY     person_id "
      + "INTO         :{bean.personId}, "
      + "             :{bean.eventCount}";
}
