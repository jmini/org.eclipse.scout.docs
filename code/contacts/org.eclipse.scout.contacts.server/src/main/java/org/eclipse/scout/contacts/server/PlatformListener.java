/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringArrayHolder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

public class PlatformListener implements IPlatformListener {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(PlatformListener.class);

  public static final String ORGANISATION1 = "Alphatom";
  public static final String ORGANISATION2 = "Bignix";
  public static final String ORGANISATION3 = "Flextexon";
  public static final String ORGANISATION4 = "Hottrax";
  public static final String ORGANISATION5 = "Strongnix";
  public static final String ORGANISATION6 = "Techdexon";

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
    }
  }

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoCreateProperty.class)) {

      try {
        ServerRunContext superUserRunContext = BEANS.get(SuperUserRunContextProvider.class).provide();
        superUserRunContext.run(new IRunnable() {

          @Override
          public void run() throws Exception {
            Set<String> tables = getExistingTables();
            createOrganizationTable(tables);
            createPersonTable(tables);
          }
        });
      }
      catch (ProcessingException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createOrganizationTable(Set<String> tables) {
    if (!tables.contains("ORGANIZATION")) {
      SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
      LOG.info("Database table 'ORGANIZATION' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createOrganizationEntry(ORGANISATION1, "London", "GB", "alphat.oi", "file:///C:/imgs/company/alphatom.png");
        createOrganizationEntry(ORGANISATION2, "Birmingham", "GB", "bignix.com", "file:///C:/imgs/company/bignix.png");
        createOrganizationEntry(ORGANISATION3, "Paris", "FR", "flextexon.com", "file:///C:/imgs/company/flextexon.png");
        createOrganizationEntry(ORGANISATION4, "New-York", "US", "hottrax.fr", "file:///C:/imgs/company/hottrax.png");
        createOrganizationEntry(ORGANISATION5, "Boston", "US", "strongnix.net", "file:///C:/imgs/company/strongnix.png");
        createOrganizationEntry(ORGANISATION6, "New-York", "US", "techdexon.com", "file:///C:/imgs/company/techdexon.png");

        LOG.info("Database table 'ORGANIZATION' populated with sample data");
      }
    }
  }

  private void createOrganizationEntry(String name, String city, String country, String url, String logoUrl) {
    SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE_DATA, new NVPair("organization_id", UUID.randomUUID().toString()), new NVPair("name", name), new NVPair("city", city), new NVPair("country", country), new NVPair("url", url),
        new NVPair("logoUrl", logoUrl));
  }

  private void createPersonTable(Set<String> tables) {
    if (!tables.contains("PERSON")) {
      SQL.insert(SQLs.PERSON_CREATE_TABLE);
      LOG.info("Database table 'PERSON' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createPersonEntry("Anna", "Mills", "M", "anna.mills@mail.com", null, "file:///C:/imgs/person/1.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry("Melody", "Bergnaum", "F", "mbergnaum@mail.com", null, "file:///C:/imgs/person/2.jpg", "Portland", "US", null, ORGANISATION6);
        createPersonEntry("Bart", "Zulauf", "M", "bart@zulauf.com", null, "file:///C:/imgs/person/3.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry("Wilmer", "Fay", "M", "wil65@email.com", null, "file:///C:/imgs/person/4.jpg", "Virginia Beach", "US", null, ORGANISATION4);
        createPersonEntry("Axel", "Hahn", "M", "a.hahn@company.com", null, "file:///C:/imgs/person/5.jpg", "Chandler", "US", null, ORGANISATION4);
        createPersonEntry("Hermina", "Borer", "F", "herborer@adomain.net", null, "file:///C:/imgs/person/6.jpg", "Birmingham", "GB", null, ORGANISATION5);
        createPersonEntry("Peter", "Crist", "M", "pchrist@me.com", null, "file:///C:/imgs/person/7.jpg", "Portland", "US", null, ORGANISATION1);
        createPersonEntry("Daisha", "Rohan", "F", "", null, "file:///C:/imgs/person/8.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry("Lonnie", "Marks", "M", "lmarks@mail.org", null, "file:///C:/imgs/person/9.jpg", "London", "GB", null, ORGANISATION5);
        createPersonEntry("Evert", "Johnson", "M", "evert@me.com", null, "file:///C:/imgs/person/10.jpg", "Birmingham", "GB", null, ORGANISATION1);
        createPersonEntry("Holly", "Beier", "M", "holly@beier.net", null, "file:///C:/imgs/person/11.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry("Grant", "Reichel", "M", "g_reichel@themail.com", null, "file:///C:/imgs/person/12.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry("Xander", "Spinka", "M", "spinka@oleta.us", null, "file:///C:/imgs/person/13.jpg", "Portland", "US", null, ORGANISATION2);
        createPersonEntry("Cara", "Turcotte", "F", "cara@cara.us", null, "file:///C:/imgs/person/14.jpg", "Virginia Beach", "US", null, ORGANISATION3);
        createPersonEntry("Jade", "McLaughlin", "F", "jade@mcl.com", null, "file:///C:/imgs/person/15.jpg", "Portland", "US", null, ORGANISATION5);
        createPersonEntry("Kevon", "Funk", "M", "kev@funk.de", null, "file:///C:/imgs/person/16.jpg", "London", "GB", null, ORGANISATION1);
        createPersonEntry("Pearlie", "Hammes", "F", "pearlie@email.net", null, null, "Virginia Beach", "US", null, ORGANISATION3);
        createPersonEntry("John", "Berge", "M", "john89@mail.com", null, "file:///C:/imgs/person/18.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry("Adolph", "Maggio", "M", "", null, "file:///C:/imgs/person/19.jpg", "London", "GB", null, ORGANISATION1);
        createPersonEntry("Emmie", "Parisian", "F", "emmie_parisian@email.net", null, null, "Chandler", "US", null, ORGANISATION2);

        LOG.info("Database table 'PERSON' populated with sample data");
      }
    }
  }

  public void createPersonEntry(String firstName, String lastName, String gender, String email, Date dob, String pictureUrl, String city, String country, String position, String organizationId) {
    SQL.insert(SQLs.PERSON_INSERT_SAMPLE_DATA,
        new NVPair("person_id", UUID.randomUUID().toString()),
        new NVPair("firstName", firstName),
        new NVPair("lastName", lastName),
        new NVPair("email", email),
        new NVPair("pictureUrl", pictureUrl),
        new NVPair("dob", dob),
        new NVPair("gender", gender),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("position", position),
        new NVPair("organizationId", organizationId));
  }
}
