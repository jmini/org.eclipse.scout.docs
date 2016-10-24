package org.eclipse.scout.contacts.events.server.datasource;

import org.eclipse.scout.contacts.events.shared.person.PersonChooserFormData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public class DatastorePersonChooser {

  public void store(PersonChooserFormData createParticipantFormData) {
  }

  public void clear() {
  }

  //TODO
//  @Override
//  protected AbstractPropertyData<String> computeId(PersonChooserFormData formData) {
//    return null;
//  }

  /**
   * @param pageData
   * @param organizationId
   */
  public void loadAllInto(PersonTablePageData pageData, String organizationId) {
//    for (EventFormData formData : getAll()) {
//      if (organizationId != null && organizationId.equals(formData.getOrganization().getValue())) {
//        PersonTableRowData rowData = pageData.addRow();
//
//        rowData.setCity(formData.getCity().getValue());
//        rowData.setCountry(formData.getCountry().getValue());
//        rowData.setEmail(formData.getEmail().getValue());
//        rowData.setFirstName(formData.getFirstName().getValue());
//        rowData.setLastName(formData.getLastName().getValue());
//        rowData.setMobile(formData.getPhone().getValue());
//        rowData.setOrganization(formData.getOrganization().getValue());
//        rowData.setPersonId(formData.getPersonId());
//        rowData.setPhone(formData.getPhone().getValue());
//      }
//    }
  }

}
