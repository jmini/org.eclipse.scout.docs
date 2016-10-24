//package org.eclipse.scout.contacts.events.server.datasource;
//
//import org.eclipse.scout.contacts.events.shared.location.LocationFormData;
//import org.eclipse.scout.contacts.events.shared.location.LocationFormData.LocationIdProperty;
//import org.eclipse.scout.contacts.events.shared.location.LocationTablePageData;
//import org.eclipse.scout.contacts.events.shared.location.LocationTablePageData.LocationTableRowData;
//import org.eclipse.scout.contacts.server.datasource.AbstractDataStore;
//import org.eclipse.scout.rt.platform.ApplicationScoped;
//
//@ApplicationScoped
//public class DatastoreLocation extends AbstractDataStore<LocationFormData> {
//
//  @Override
//  protected LocationIdProperty computeId(LocationFormData formData) {
//    return formData.getLocationIdProperty();
//  }
//
//  public void loadAllInto(LocationTablePageData pageData) {
//    for (LocationFormData formData : getAll()) {
//      LocationTableRowData rowData = pageData.addRow();
//      rowData.setLocationId(formData.getLocationId());
//      rowData.setName(formData.getName().getValue());
//      rowData.setCity(formData.getCity().getValue());
//      rowData.setCountry(formData.getCountry().getValue());
//    }
//  }
//}
