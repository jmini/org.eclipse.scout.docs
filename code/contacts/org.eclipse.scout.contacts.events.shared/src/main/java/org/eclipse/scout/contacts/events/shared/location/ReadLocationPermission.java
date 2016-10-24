package org.eclipse.scout.contacts.events.shared.location;

import java.security.BasicPermission;

public class ReadLocationPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ReadLocationPermission() {
    super(ReadLocationPermission.class.getSimpleName());
  }
}
