package org.eclipse.scout.contacts.server.sql;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.contacts.server.SuperUserRunContextProducer;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DBSetupService {
  private static final Logger LOG = LoggerFactory.getLogger(DBSetupService.class);

  public static final UUID ORGANISATION1 = UUID.randomUUID();
  public static final UUID ORGANISATION2 = UUID.randomUUID();
  public static final UUID ORGANISATION3 = UUID.randomUUID();
  public static final UUID ORGANISATION4 = UUID.randomUUID();
  public static final UUID ORGANISATION5 = UUID.randomUUID();
  public static final UUID ORGANISATION6 = UUID.randomUUID();

  public static final UUID PERSON01 = UUID.randomUUID();
  public static final UUID PERSON02 = UUID.randomUUID();
  public static final UUID PERSON03 = UUID.randomUUID();
  public static final UUID PERSON04 = UUID.randomUUID();
  public static final UUID PERSON05 = UUID.randomUUID();
  public static final UUID PERSON06 = UUID.randomUUID();
  public static final UUID PERSON07 = UUID.randomUUID();
  public static final UUID PERSON08 = UUID.randomUUID();
  public static final UUID PERSON09 = UUID.randomUUID();
  public static final UUID PERSON10 = UUID.randomUUID();
  public static final UUID PERSON11 = UUID.randomUUID();
  public static final UUID PERSON12 = UUID.randomUUID();
  public static final UUID PERSON13 = UUID.randomUUID();
  public static final UUID PERSON14 = UUID.randomUUID();
  public static final UUID PERSON15 = UUID.randomUUID();
  public static final UUID PERSON16 = UUID.randomUUID();
  public static final UUID PERSON17 = UUID.randomUUID();
  public static final UUID PERSON18 = UUID.randomUUID();
  public static final UUID PERSON19 = UUID.randomUUID();
  public static final UUID PERSON20 = UUID.randomUUID();

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoCreateProperty.class)) {

      try {
        BEANS.get(SuperUserRunContextProducer.class).produce().run(new IRunnable() {

          @Override
          public void run() throws Exception {
            Set<String> tables = getExistingTables();
            createOrganizationTable(tables);
            createPersonTable(tables);
          }
        });
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  public void createOrganizationTable() {
    createOrganizationTable(getExistingTables());
  }

  private void createOrganizationTable(Set<String> tables) {
    if (!tables.contains("ORGANIZATION")) {
      SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
      LOG.info("Database table 'ORGANIZATION' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createOrganizationEntry(ORGANISATION1, "Alphatom", "London", "GB", "alphat.oi", "profiles/company/alphatom.png");
        createOrganizationEntry(ORGANISATION2, "Bignix", "Birmingham", "GB", "bignix.com", "profiles/company/bignix.png");
        createOrganizationEntry(ORGANISATION3, "Flextexon", "Paris", "FR", "flextexon.com", "profiles/company/flextexon.png");
        createOrganizationEntry(ORGANISATION4, "Hottrax", "New-York", "US", "hottrax.fr", "profiles/company/hottrax.png");
        createOrganizationEntry(ORGANISATION5, "Strongnix", "Boston", "US", "strongnix.net", "profiles/company/strongnix.png");
        createOrganizationEntry(ORGANISATION6, "Techdexon", "New-York", "US", "techdexon.com", "profiles/company/techdexon.png");

        LOG.info("Database table 'ORGANIZATION' populated with sample data");
      }
    }
  }

  private void createOrganizationEntry(UUID uuid, String name, String city, String country, String url, String logoUrl) {
    SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE_DATA,
        new NVPair("organizationUuid", uuid.toString()),
        new NVPair("name", name), new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("url", url),
        new NVPair("logoUrl", logoUrl));
  }

  private void createPersonTable(Set<String> tables) {
    if (!tables.contains("PERSON")) {
      SQL.insert(SQLs.PERSON_CREATE_TABLE);
      LOG.info("Database table 'PERSON' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createPersonEntry(PERSON01, "Anna", "Mills", "M", "anna.mills@mail.com", null, "profiles/person/1.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry(PERSON02, "Melody", "Bergnaum", "F", "mbergnaum@mail.com", null, "profiles/person/2.jpg", "Portland", "US", null, ORGANISATION6);
        createPersonEntry(PERSON03, "Bart", "Zulauf", "M", "bart@zulauf.com", null, "profiles/person/3.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry(PERSON04, "Wilmer", "Fay", "M", "wil65@email.com", null, "profiles/person/4.jpg", "Virginia Beach", "US", null, ORGANISATION4);
        createPersonEntry(PERSON05, "Axel", "Hahn", "M", "a.hahn@company.com", null, "profiles/person/5.jpg", "Chandler", "US", null, ORGANISATION4);
        createPersonEntry(PERSON06, "Hermina", "Borer", "F", "herborer@adomain.net", null, "profiles/person/6.jpg", "Birmingham", "GB", null, ORGANISATION5);
        createPersonEntry(PERSON07, "Peter", "Crist", "M", "pchrist@me.com", null, "profiles/person/7.jpg", "Portland", "US", null, ORGANISATION1);
        createPersonEntry(PERSON08, "Daisha", "Rohan", "F", "", null, "profiles/person/8.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry(PERSON09, "Lonnie", "Marks", "M", "lmarks@mail.org", null, "profiles/person/9.jpg", "London", "GB", null, ORGANISATION5);
        createPersonEntry(PERSON10, "Evert", "Johnson", "M", "evert@me.com", null, "profiles/person/10.jpg", "Birmingham", "GB", null, ORGANISATION1);
        createPersonEntry(PERSON11, "Holly", "Beier", "M", "holly@beier.net", null, "profiles/person/11.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry(PERSON12, "Grant", "Reichel", "M", "g_reichel@themail.com", null, "profiles/person/12.jpg", "London", "GB", null, ORGANISATION2);
        createPersonEntry(PERSON13, "Xander", "Spinka", "M", "spinka@oleta.us", null, "profiles/person/13.jpg", "Portland", "US", null, ORGANISATION2);
        createPersonEntry(PERSON14, "Cara", "Turcotte", "F", "cara@cara.us", null, "profiles/person/14.jpg", "Virginia Beach", "US", null, ORGANISATION3);
        createPersonEntry(PERSON15, "Jade", "McLaughlin", "F", "jade@mcl.com", null, "profiles/person/15.jpg", "Portland", "US", null, ORGANISATION5);
        createPersonEntry(PERSON16, "Kevon", "Funk", "M", "kev@funk.de", null, "profiles/person/16.jpg", "London", "GB", null, ORGANISATION1);
        createPersonEntry(PERSON17, "Pearlie", "Hammes", "F", "pearlie@email.net", null, null, "Virginia Beach", "US", null, ORGANISATION3);
        createPersonEntry(PERSON18, "John", "Berge", "M", "john89@mail.com", null, "profiles/person/18.jpg", "Arlington", "US", null, ORGANISATION3);
        createPersonEntry(PERSON19, "Adolph", "Maggio", "M", "", null, "profiles/person/19.jpg", "London", "GB", null, ORGANISATION1);
        createPersonEntry(PERSON20, "Emmie", "Parisian", "F", "emmie_parisian@email.net", null, null, "Chandler", "US", null, ORGANISATION2);

        LOG.info("Database table 'PERSON' populated with sample data");
      }
    }
  }

  private void createPersonEntry(UUID uuid, String firstName, String lastName, String gender, String email, Date dob, String pictureUrl, String city, String country, String position, UUID organizationUuid) {
    SQL.insert(SQLs.PERSON_INSERT_SAMPLE_DATA,
        new NVPair("personUuid", uuid.toString()),
        new NVPair("firstName", firstName),
        new NVPair("lastName", lastName),
        new NVPair("email", email),
        new NVPair("pictureUrl", pictureUrl),
        new NVPair("dob", dob),
        new NVPair("gender", gender),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("position", position),
        new NVPair("organizationUuid", organizationUuid.toString()));
  }

}
