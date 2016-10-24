package org.eclipse.scout.contacts.server.sql;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.eclipse.scout.contacts.server.datasource.DatastoreOrganization;
import org.eclipse.scout.contacts.server.datasource.DatastorePerson;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::service[]
@ApplicationScoped
@CreateImmediately
public class DatabaseSetupService implements IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(DatabaseSetupService.class);

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

  public static final UUID ORGANISATION1 = UUID.randomUUID();
  public static final UUID ORGANISATION2 = UUID.randomUUID();
  public static final UUID ORGANISATION3 = UUID.randomUUID();
  public static final UUID ORGANISATION4 = UUID.randomUUID();
  public static final UUID ORGANISATION5 = UUID.randomUUID();
  public static final UUID ORGANISATION6 = UUID.randomUUID();

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
    if (!getExistingTables().contains("ORGANIZATION")) {
      DatastoreOrganization datastore = BEANS.get(DatastoreOrganization.class);
      LOG.info("Datastore 'ORGANIZATION' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        datastore.store(createOrganizationFormData(ORGANISATION1, "Alphatom", "London", "GB", "https://alphat.oi/", "http://localhost:8082/imgs/company/alphatom.png"));
        datastore.store(createOrganizationFormData(ORGANISATION2, "Bignix", "Birmingham", "GB", "https://bignix.com/", "http://localhost:8082/imgs/company/bignix.png"));
        datastore.store(createOrganizationFormData(ORGANISATION3, "Flextexon", "Paris", "FR", "https://flextexon.com/", "http://localhost:8082/imgs/company/flextexon.png"));
        datastore.store(createOrganizationFormData(ORGANISATION4, "Hottrax", "New-York", "US", "https://hottrax.fr/", "http://localhost:8082/imgs/company/hottrax.png"));
        datastore.store(createOrganizationFormData(ORGANISATION5, "Strongnix", "Boston", "US", "https://strongnix.net/", "http://localhost:8082/imgs/company/strongnix.png"));
        datastore.store(createOrganizationFormData(ORGANISATION6, "Techdexon", "New-York", "US", "https://techdexon.com/", "http://localhost:8082/imgs/company/techdexon.png"));
        LOG.info("Datastore 'ORGANIZATION' populated with sample data");
      }
    }
  }

  private OrganizationFormData createOrganizationFormData(UUID organizationId, String name, String city, String country, String url, String logoUrl) {
    OrganizationFormData formData = new OrganizationFormData();
    formData.setOrganizationId(organizationId.toString());
    formData.getName().setValue(name);
    formData.getAddressBox().getCity().setValue(city);
    formData.getAddressBox().getCountry().setValue(country);
    formData.getHomepage().setValue(url);
    formData.getPicture().setUrl(logoUrl);
    return formData;
  }

  public void createPersonTable() {
    if (!getExistingTables().contains("PERSON")) {
      DatastorePerson datastore = BEANS.get(DatastorePerson.class);
      LOG.info("Database table 'PERSON' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        datastore.store(createPersonFormData(PERSON01, "Anna", "Mills", "M", "anna.mills@mail.com", null, "http://localhost:8082/imgs/person/1.jpg", "Arlington", "US", null, ORGANISATION3));
        datastore.store(createPersonFormData(PERSON02, "Melody", "Bergnaum", "F", "mbergnaum@mail.com", null, "http://localhost:8082/imgs/person/2.jpg", "Portland", "US", null, ORGANISATION6));
        datastore.store(createPersonFormData(PERSON03, "Bart", "Zulauf", "M", "bart@zulauf.com", null, "http://localhost:8082/imgs/person/3.jpg", "Arlington", "US", null, ORGANISATION3));
        datastore.store(createPersonFormData(PERSON04, "Wilmer", "Fay", "M", "wil65@email.com", null, "http://localhost:8082/imgs/person/4.jpg", "Virginia Beach", "US", null, ORGANISATION4));
        datastore.store(createPersonFormData(PERSON05, "Axel", "Hahn", "M", "a.hahn@company.com", null, "http://localhost:8082/imgs/person/5.jpg", "Chandler", "US", null, ORGANISATION4));
        datastore.store(createPersonFormData(PERSON06, "Hermina", "Borer", "F", "herborer@adomain.net", null, "http://localhost:8082/imgs/person/6.jpg", "Birmingham", "GB", null, ORGANISATION5));
        datastore.store(createPersonFormData(PERSON07, "Peter", "Crist", "M", "pchrist@me.com", null, "http://localhost:8082/imgs/person/7.jpg", "Portland", "US", null, ORGANISATION1));
        datastore.store(createPersonFormData(PERSON08, "Daisha", "Rohan", "F", "", null, "http://localhost:8082/imgs/person/8.jpg", "London", "GB", null, ORGANISATION2));
        datastore.store(createPersonFormData(PERSON09, "Lonnie", "Marks", "M", "lmarks@mail.org", null, "http://localhost:8082/imgs/person/9.jpg", "London", "GB", null, ORGANISATION5));
        datastore.store(createPersonFormData(PERSON10, "Evert", "Johnson", "M", "evert@me.com", null, "http://localhost:8082/imgs/person/10.jpg", "Birmingham", "GB", null, ORGANISATION1));
        datastore.store(createPersonFormData(PERSON11, "Holly", "Beier", "M", "holly@beier.net", null, "http://localhost:8082/imgs/person/11.jpg", "London", "GB", null, ORGANISATION2));
        datastore.store(createPersonFormData(PERSON12, "Grant", "Reichel", "M", "g_reichel@themail.com", null, "http://localhost:8082/imgs/person/12.jpg", "London", "GB", null, ORGANISATION2));
        datastore.store(createPersonFormData(PERSON13, "Xander", "Spinka", "M", "spinka@oleta.us", null, "http://localhost:8082/imgs/person/13.jpg", "Portland", "US", null, ORGANISATION2));
        datastore.store(createPersonFormData(PERSON14, "Cara", "Turcotte", "F", "cara@cara.us", null, "http://localhost:8082/imgs/person/14.jpg", "Virginia Beach", "US", null, ORGANISATION3));
        datastore.store(createPersonFormData(PERSON15, "Jade", "McLaughlin", "F", "jade@mcl.com", null, "http://localhost:8082/imgs/person/15.jpg", "Portland", "US", null, ORGANISATION5));
        datastore.store(createPersonFormData(PERSON16, "Kevon", "Funk", "M", "kev@funk.de", null, "http://localhost:8082/imgs/person/16.jpg", "London", "GB", null, ORGANISATION1));
        datastore.store(createPersonFormData(PERSON17, "Pearlie", "Hammes", "F", "pearlie@email.net", null, null, "Virginia Beach", "US", null, ORGANISATION3));
        datastore.store(createPersonFormData(PERSON18, "John", "Berge", "M", "john89@mail.com", null, "http://localhost:8082/imgs/person/18.jpg", "Arlington", "US", null, ORGANISATION3));
        datastore.store(createPersonFormData(PERSON19, "Adolph", "Maggio", "M", "", null, "http://localhost:8082/imgs/person/19.jpg", "London", "GB", null, ORGANISATION1));
        datastore.store(createPersonFormData(PERSON20, "Emmie", "Parisian", "F", "emmie_parisian@email.net", null, null, "Chandler", "US", null, ORGANISATION2));

        // tag::service[]
        LOG.info("Database table 'PERSON' populated with sample data");
      }
    }
  }

  public PersonFormData createPersonFormData(UUID id, String firstName, String lastName, String gender, String email, Date dob, String pictureUrl, String city, String country, String position, UUID organizationId) {
    PersonFormData formData = new PersonFormData();
    formData.setPersonId(id.toString());
    formData.getFirstName().setValue(firstName);
    formData.getLastName().setValue(lastName);
    formData.getGenderGroup().setValue(gender);
    formData.getEmail().setValue(email);
    formData.getDateOfBirth().setValue(dob);
    formData.getPictureUrl().setValue(pictureUrl);
    formData.getCity().setValue(city);
    formData.getCountry().setValue(country);
    formData.getPosition().setValue(position);
    formData.getOrganization().setValue(organizationId.toString());
    return formData;
  }

  private Set<String> getExistingTables() {
    return Collections.emptySet();
  }
  // end::service[]

  @Override
  public void dropDataStore() {
    BEANS.get(DatastorePerson.class).clear();
    BEANS.get(DatastoreOrganization.class).clear();
  }

  @Override
  public void createDataStore() {
    createOrganizationTable();
    createPersonTable();
  }
  // tag::service[]
}
// end::service[]
