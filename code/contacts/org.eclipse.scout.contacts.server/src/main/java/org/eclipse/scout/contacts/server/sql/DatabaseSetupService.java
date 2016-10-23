package org.eclipse.scout.contacts.server.sql;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::service[]
@ApplicationScoped
@CreateImmediately
public class DatabaseSetupService implements IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(DatabaseSetupService.class);

  public static final String PERSON01 = "1002924d";
  public static final String PERSON02 = "37ee1e05";
  public static final String PERSON03 = "2d972f23";
  public static final String PERSON04 = "4f923735";
  public static final String PERSON05 = "9c9bf45c";
  public static final String PERSON06 = "45edc91c";
  public static final String PERSON07 = "ae31a113";
  public static final String PERSON08 = "cda79961";
  public static final String PERSON09 = "7d383d53";
  public static final String PERSON10 = "30f132ba";
  public static final String PERSON11 = "760e602e";
  public static final String PERSON12 = "1a3e3919";
  public static final String PERSON13 = "ca6fc99f";
  public static final String PERSON14 = "dcd1c176";
  public static final String PERSON15 = "dc72c844";
  public static final String PERSON16 = "fda31b4c";
  public static final String PERSON17 = "d42ccc26";
  public static final String PERSON18 = "86855165";
  public static final String PERSON19 = "14ae613f";
  public static final String PERSON20 = "8e883a9c";

  public static final String ORGANISATION1 = "cc0e5f05";
  public static final String ORGANISATION2 = "e7b9d594";
  public static final String ORGANISATION3 = "8245d851";
  public static final String ORGANISATION4 = "634c1ff1";
  public static final String ORGANISATION5 = "f9da661f";
  public static final String ORGANISATION6 = "2076b6c2";

  @PostConstruct
  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoCreateProperty.class)) {
      try {
        RunContext context = BEANS.get(SuperUserRunContextProducer.class).produce();
        IRunnable runnable = new IRunnable() {

          @Override
          public void run() throws Exception {
            createOrganizationTable();
            createPersonTable();
          }
        };

        context.run(runnable);
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  public void createOrganizationTable() {
    SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
    LOG.info("Database table 'ORGANIZATION' created");

    createOrganizationEntry(ORGANISATION1, "Alphatom", "London", "GB", "https://alphat.oi/", "file:///C:/imgs/company/alphatom.png");
    createOrganizationEntry(ORGANISATION2, "Bignix", "Birmingham", "GB", "https://bignix.com/", "file:///C:/imgs/company/bignix.png");
    createOrganizationEntry(ORGANISATION3, "Flextexon", "Paris", "FR", "https://flextexon.com/", "file:///C:/imgs/company/flextexon.png");
    createOrganizationEntry(ORGANISATION4, "Hottrax", "New-York", "US", "https://hottrax.fr/", "file:///C:/imgs/company/hottrax.png");
    createOrganizationEntry(ORGANISATION5, "Strongnix", "Boston", "US", "https://strongnix.net/", "file:///C:/imgs/company/strongnix.png");
    createOrganizationEntry(ORGANISATION6, "Techdexon", "New-York", "US", "https://techdexon.com/", "file:///C:/imgs/company/techdexon.png");

    LOG.info("Database table 'ORGANIZATION' populated with sample data");
  }

  private void createOrganizationEntry(String organizationId, String name, String city, String country, String url, String logoUrl) {
    SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE_DATA, new NVPair("organization_id", organizationId), new NVPair("name", name), new NVPair("city", city), new NVPair("country", country), new NVPair("url", url),
        new NVPair("logoUrl", logoUrl));
  }

  private void createPersonTable() {
    SQL.insert(SQLs.PERSON_CREATE_TABLE);
    LOG.info("Database table 'PERSON' created");

    createPersonEntry(PERSON01, "Anna", "Mills", "M", "anna.mills@mail.com", null, "file:///C:/imgs/person/1.jpg", "Arlington", "US", null, ORGANISATION3);
    createPersonEntry(PERSON02, "Melody", "Bergnaum", "F", "mbergnaum@mail.com", null, "file:///C:/imgs/person/2.jpg", "Portland", "US", null, ORGANISATION6);
    createPersonEntry(PERSON03, "Bart", "Zulauf", "M", "bart@zulauf.com", null, "file:///C:/imgs/person/3.jpg", "Arlington", "US", null, ORGANISATION3);
    createPersonEntry(PERSON04, "Wilmer", "Fay", "M", "wil65@email.com", null, "file:///C:/imgs/person/4.jpg", "Virginia Beach", "US", null, ORGANISATION4);
    createPersonEntry(PERSON05, "Axel", "Hahn", "M", "a.hahn@company.com", null, "file:///C:/imgs/person/5.jpg", "Chandler", "US", null, ORGANISATION4);
    createPersonEntry(PERSON06, "Hermina", "Borer", "F", "herborer@adomain.net", null, "file:///C:/imgs/person/6.jpg", "Birmingham", "GB", null, ORGANISATION5);
    createPersonEntry(PERSON07, "Peter", "Crist", "M", "pchrist@me.com", null, "file:///C:/imgs/person/7.jpg", "Portland", "US", null, ORGANISATION1);
    createPersonEntry(PERSON08, "Daisha", "Rohan", "F", "", null, "file:///C:/imgs/person/8.jpg", "London", "GB", null, ORGANISATION2);
    createPersonEntry(PERSON09, "Lonnie", "Marks", "M", "lmarks@mail.org", null, "file:///C:/imgs/person/9.jpg", "London", "GB", null, ORGANISATION5);
    createPersonEntry(PERSON10, "Evert", "Johnson", "M", "evert@me.com", null, "file:///C:/imgs/person/10.jpg", "Birmingham", "GB", null, ORGANISATION1);
    createPersonEntry(PERSON11, "Holly", "Beier", "M", "holly@beier.net", null, "file:///C:/imgs/person/11.jpg", "London", "GB", null, ORGANISATION2);
    createPersonEntry(PERSON12, "Grant", "Reichel", "M", "g_reichel@themail.com", null, "file:///C:/imgs/person/12.jpg", "London", "GB", null, ORGANISATION2);
    createPersonEntry(PERSON13, "Xander", "Spinka", "M", "spinka@oleta.us", null, "file:///C:/imgs/person/13.jpg", "Portland", "US", null, ORGANISATION2);
    createPersonEntry(PERSON14, "Cara", "Turcotte", "F", "cara@cara.us", null, "file:///C:/imgs/person/14.jpg", "Virginia Beach", "US", null, ORGANISATION3);
    createPersonEntry(PERSON15, "Jade", "McLaughlin", "F", "jade@mcl.com", null, "file:///C:/imgs/person/15.jpg", "Portland", "US", null, ORGANISATION5);
    createPersonEntry(PERSON16, "Kevon", "Funk", "M", "kev@funk.de", null, "file:///C:/imgs/person/16.jpg", "London", "GB", null, ORGANISATION1);
    createPersonEntry(PERSON17, "Pearlie", "Hammes", "F", "pearlie@email.net", null, null, "Virginia Beach", "US", null, ORGANISATION3);
    createPersonEntry(PERSON18, "John", "Berge", "M", "john89@mail.com", null, "file:///C:/imgs/person/18.jpg", "Arlington", "US", null, ORGANISATION3);
    createPersonEntry(PERSON19, "Adolph", "Maggio", "M", "", null, "file:///C:/imgs/person/19.jpg", "London", "GB", null, ORGANISATION1);
    createPersonEntry(PERSON20, "Emmie", "Parisian", "F", "emmie_parisian@email.net", null, null, "Chandler", "US", null, ORGANISATION2);

    LOG.info("Database table 'PERSON' populated with sample data");
  }

  public void createPersonEntry(String id, String firstName, String lastName, String gender, String email, Date dob, String pictureUrl, String city, String country, String position, String organizationId) {
    SQL.insert(SQLs.PERSON_INSERT_SAMPLE_DATA,
        new NVPair("person_id", id),
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

  // end::service[]

  @Override
  public void dropDataStore() {
    SQL.update(SQLs.PERSON_DROP_TABLE);
    SQL.update(SQLs.ORGANIZATION_DROP_TABLE);
  }

  @Override
  public void createDataStore() {
    createOrganizationTable();
    createPersonTable();
  }
  // tag::service[]
}
// end::service[]
