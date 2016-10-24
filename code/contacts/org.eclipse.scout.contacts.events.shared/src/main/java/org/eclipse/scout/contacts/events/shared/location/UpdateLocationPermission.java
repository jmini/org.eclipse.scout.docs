package org.eclipse.scout.contacts.events.shared.location;

import java.security.BasicPermission;

public class UpdateLocationPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public UpdateLocationPermission() {
    super(UpdateLocationPermission.class.getSimpleName());
  }
}
