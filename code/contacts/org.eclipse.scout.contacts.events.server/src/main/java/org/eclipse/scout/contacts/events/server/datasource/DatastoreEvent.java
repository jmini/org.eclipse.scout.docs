package org.eclipse.scout.contacts.events.server.datasource;

import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.event.EventFormData.EventIdProperty;
import org.eclipse.scout.contacts.events.shared.event.EventTablePageData;
import org.eclipse.scout.contacts.events.shared.event.EventTablePageData.EventTableRowData;
import org.eclipse.scout.contacts.server.datasource.AbstractDataStore;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public class DatastoreEvent extends AbstractDataStore<EventFormData> {

  @Override
  protected EventIdProperty computeId(EventFormData formData) {
    return formData.getEventIdProperty();
  }

  /**
   * @param pageData
   * @param organizationId
   */
  public void loadAllInto(EventTablePageData pageData, String organizationId) {
    //TODO handle organizationId;
    for (EventFormData formData : getAll()) {
      EventTableRowData rowData = pageData.addRow();

      rowData.setCity(formData.getLocationBox().getCity().getValue());
      rowData.setCountry(formData.getLocationBox().getCountry().getValue());
      rowData.setEnds(formData.getEnds().getValue());
      rowData.setEventId(formData.getEventId());
      rowData.setHomepage(formData.getHomepage().getValue());
      rowData.setParticipants(0);
      rowData.setStarts(formData.getStarts().getValue());
      rowData.setTitle(formData.getTitle().getValue());
    }

  }
}
