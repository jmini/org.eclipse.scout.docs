package org.eclipse.scout.contacts.server.datasource;

import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.PersonFormData.PersonIdProperty;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData.PersonTableRowData;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public class DatastorePerson extends AbstractDataStore<PersonFormData> {

  @Override
  protected PersonIdProperty computeId(PersonFormData formData) {
    return formData.getPersonIdProperty();
  }

  /**
   * @param pageData
   * @param organizationId
   */
  public void loadAllInto(PersonTablePageData pageData, String organizationId) {
    for (PersonFormData formData : getAll()) {
      if (organizationId == null || organizationId.equals(formData.getOrganization().getValue())) {
        PersonTableRowData rowData = pageData.addRow();

        rowData.setCity(formData.getCity().getValue());
        rowData.setCountry(formData.getCountry().getValue());
        rowData.setEmail(formData.getEmail().getValue());
        rowData.setFirstName(formData.getFirstName().getValue());
        rowData.setLastName(formData.getLastName().getValue());
        rowData.setMobile(formData.getPhone().getValue());
        rowData.setOrganization(formData.getOrganization().getValue());
        rowData.setPersonId(formData.getPersonId());
        rowData.setPhone(formData.getPhone().getValue());
      }
    }

  }
}
