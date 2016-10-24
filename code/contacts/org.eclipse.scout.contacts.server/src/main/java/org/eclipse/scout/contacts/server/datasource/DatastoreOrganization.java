package org.eclipse.scout.contacts.server.datasource;

import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData.OrganizationIdProperty;
import org.eclipse.scout.contacts.shared.organization.OrganizationTablePageData;
import org.eclipse.scout.contacts.shared.organization.OrganizationTablePageData.OrganizationTableRowData;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public class DatastoreOrganization extends AbstractDataStore<OrganizationFormData> {

  @Override
  protected OrganizationIdProperty computeId(OrganizationFormData formData) {
    return formData.getOrganizationIdProperty();
  }

  public void loadAllInto(OrganizationTablePageData pageData) {
    for (OrganizationFormData formData : getAll()) {
      OrganizationTableRowData rowData = pageData.addRow();

      rowData.setOrganizationId(formData.getOrganizationId());
      rowData.setName(formData.getName().getValue());
      rowData.setCity(formData.getAddressBox().getCity().getValue());
      rowData.setCountry(formData.getAddressBox().getCountry().getValue());
      rowData.setHomepage(formData.getHomepage().getValue());
    }
  }
}
