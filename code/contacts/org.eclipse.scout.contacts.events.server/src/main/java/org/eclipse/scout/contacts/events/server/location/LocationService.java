package org.eclipse.scout.contacts.events.server.location;

import org.eclipse.scout.contacts.events.server.datasource.DatastoreLocation;
import org.eclipse.scout.contacts.events.shared.location.CreateLocationPermission;
import org.eclipse.scout.contacts.events.shared.location.ILocationService;
import org.eclipse.scout.contacts.events.shared.location.LocationFormData;
import org.eclipse.scout.contacts.events.shared.location.LocationTablePageData;
import org.eclipse.scout.contacts.events.shared.location.ReadLocationPermission;
import org.eclipse.scout.contacts.events.shared.location.UpdateLocationPermission;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

public class LocationService implements ILocationService {

  @Override
  public LocationFormData prepareCreate(LocationFormData formData) {
    if (!ACCESS.check(new CreateLocationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return formData;
  }

  @Override
  public LocationFormData create(LocationFormData formData) {
    if (!ACCESS.check(new CreateLocationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    BEANS.get(DatastoreLocation.class).store(formData);
    return formData;
  }

  @Override
  public LocationFormData load(LocationFormData formData) {
    if (!ACCESS.check(new ReadLocationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return BEANS.get(DatastoreLocation.class).load(formData);
  }

  @Override
  public LocationFormData store(LocationFormData formData) {
    if (!ACCESS.check(new UpdateLocationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    BEANS.get(DatastoreLocation.class).store(formData);
    return formData;
  }

  @Override
  public LocationTablePageData getLocationTableData(SearchFilter filter) {
    LocationTablePageData pageData = new LocationTablePageData();

    BEANS.get(DatastoreLocation.class).loadAllInto(pageData);
    return pageData;
  }
}
